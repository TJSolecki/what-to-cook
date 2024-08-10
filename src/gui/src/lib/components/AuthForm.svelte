<script lang="ts">
    import { Label } from "$lib/components/ui/label";
    import { Input } from "$lib/components/ui/input";
    import { Button } from "$lib/components/ui/button";
    import { LoaderIcon } from "lucide-svelte";

    export let isLoading: boolean;
    export let email: string;
    export let password: string;
</script>

<div class="grid gap-2">
    <div class="grid gap-1">
        <Label for="email">Email</Label>
        <Input
            id="email"
            placeholder="name@example.com"
            type="email"
            autocapitalize="none"
            autocomplete="email"
            autocorrect="off"
            disabled={isLoading}
            bind:value={email}
        />
    </div>
    <div class="grid gap-1">
        <Label for="password">Password</Label>
        <Input
            id="password"
            type="password"
            disabled={isLoading}
            bind:value={password}
        />
    </div>
    <Button type="submit" disabled={isLoading}>
        {#if isLoading}
            <LoaderIcon class="mr-2 h-4 w-4 animate-spin" />
        {/if}
        Sign In
    </Button>
</div>
