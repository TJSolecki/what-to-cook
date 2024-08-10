import { toast } from "svelte-sonner";
import type { AuthResponse } from "src/types/types";
import { navigate } from "svelte-routing";
import { userId } from "../../stores";

type RequestOptions<T> = {
    method?: "GET" | "POST";
    json?: Object;
};

export async function sendRequest<T>(
    url: string,
    options: RequestOptions<T> = {},
): Promise<T> {
    const { method = "GET", json } = options;

    const headers = new Headers();
    if (json) {
        headers.append("Content-Type", "application/json");
    }
    const req = new Request(url, {
        method,
        body: json ? JSON.stringify(json) : undefined,
        headers,
    });

    const res = await fetch(req);
    if (res.ok) {
        return res.json();
    }

    const description = `${method} request to ${url} failed with ${res.status} ${res.statusText}`;
    toast.error("Error", { description });
    throw new Error(description);
}

export async function onAuthFormSubmit(
    endpoint: "login" | "register",
    hashedPassword: string,
    email: string,
) {
    const res = await sendRequest<AuthResponse>(`/api/auth/${endpoint}`, {
        method: "POST",
        json: { email, password: hashedPassword },
    });
    if (res?.userId !== undefined) {
        userId.set(res.userId);
        navigate("/");
    }
}
