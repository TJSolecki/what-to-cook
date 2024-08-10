<script lang="ts">
    import { hashValue } from "../utils/hashing";
    import AuthForm from "./AuthForm.svelte";

    let email = "";
    let password = "";
    let isLoading = false;

    let hashedPassword = "";
    $: hashValue(password).then((hash) => {
        hashedPassword = hash;
    });
    function onSubmit() {
        const formData = new FormData();
        formData.set("email", email);
        formData.set("password", hashedPassword);

        fetch("/api/auth/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: formData,
        });
    }
</script>

<form on:submit|preventDefault={onSubmit} class="max-w-[20rem] mx-auto">
    <div class="mb-8">
        <h1 class="text-2xl font-semibold tracking-tight mt-0">Login</h1>
        <p class="text-muted-foreground text-sm">
            Enter your login credentials below
        </p>
    </div>
    <AuthForm bind:password bind:email {isLoading} />
</form>
