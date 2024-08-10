import { toast } from "svelte-sonner";

type RequestOptions<T> = {
    method?: "GET" | "POST";
    json?: T;
};

export async function sendRequest<T>(
    url: string,
    options: RequestOptions<T> = {},
): Promise<T> {
    const { method = "GET", json } = options;

    const headers = new Headers({ Accept: "application/json" });
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
