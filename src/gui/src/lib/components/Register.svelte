<script lang="ts">
  import { Button } from "$lib/components/ui/button";
  import { Label } from "$lib/components/ui/label";
  import { Input } from "$lib/components/ui/input";
  import { LoaderIcon } from "lucide-svelte";

  let email = "";
  let password = "";
  let isLoading = false;
  function onSubmit() {}
</script>

<form on:submit|preventDefault={onSubmit} class="max-w-[20rem] mx-auto">
  <div>
    <h1 class="text-2xl font-semibold tracking-tight mt-0">
      Create an account
    </h1>
    <p class="text-muted-foreground text-sm">
      Enter your email below to create your account
    </p>
  </div>
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
      Register
    </Button>
  </div>
</form>
