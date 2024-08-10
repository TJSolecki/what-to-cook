<script lang="ts">
    import { onAuthFormSubmit } from "$lib/utils/fetch";
    import { hashValue } from "../utils/hashing";
    import AuthForm from "./AuthForm.svelte";
    import { Button } from "./ui/button";

    export let authType: "login" | "register";

    let email = "";
    let password = "";
    let isLoading = false;

    let hashedPassword = "";
    $: hashValue(password).then((hash) => {
        hashedPassword = hash;
    });
</script>

<form
    on:submit|preventDefault={() =>
        onAuthFormSubmit(authType, hashedPassword, email)}
    class="max-w-[20rem] mx-auto"
>
    <div class="mb-4">
        <h1 class="text-2xl font-semibold tracking-tight mt-0">
            {authType === "login" ? "Login" : "Create an account"}
        </h1>
        <p class="text-muted-foreground text-sm">
            {#if authType === "login"}
                Enter your login credentials below
            {:else}
                Enter your email below to create your account
            {/if}
        </p>
    </div>
    <AuthForm bind:password bind:email {isLoading} />
    <div class="flex justify-center">
        {#if authType === "login"}
            <Button class="text-muted-foreground" variant="link" href="register"
                >Don't have an account yet? Click here to register.</Button
            >
        {:else}
            <Button class="text-muted-foreground" variant="link" href="login"
                >Already have an account? Login here.</Button
            >
        {/if}
    </div>
</form>
