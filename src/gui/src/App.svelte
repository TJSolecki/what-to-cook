<script lang="ts">
    import Moon from "lucide-svelte/icons/moon";
    import Sun from "lucide-svelte/icons/sun";
    import { ModeWatcher, toggleMode, mode } from "mode-watcher";
    import { Switch } from "$lib/components/ui/switch";
    import Recipes from "$lib/components/Recipes.svelte";
    import { Router, Route } from "svelte-routing";
    import { CookingPot } from "lucide-svelte";
    import { Toaster } from "svelte-sonner";
    import AuthPrompt from "$lib/components/AuthPrompt.svelte";
    import Home from "$lib/views/Home.svelte";
</script>

<ModeWatcher />
<Toaster />
<Router>
    <header
        class="sticky top-0 z-50 w-full border-b border-border/40 bg-background/95 backdrop-blur
  supports-[backdrop-filter]:bg-background/60"
    >
        <div
            class="container flex h-14 max-w-screen-2xl items-center justify-between"
        >
            <a href="/" class="flex items-center gap-2">
                <CookingPot class="text-primary w-6 h-6" strokeWidth={1.5} />
                <h1 class="scroll-m-20 text-xl font-semibold tracking-tight">
                    what to cook
                </h1>
            </a>
            <div class="flex items-center gap-2">
                {#if $mode === "dark"}
                    <Moon />
                {:else}
                    <Sun />
                {/if}
                <Switch onCheckedChange={toggleMode} />
            </div>
        </div>
    </header>
    <main class="max-w-screen-xl w-[80%] mx-auto py-4">
        <Route path="/">
            <Home />
        </Route>
        <Route path="/recipes">
            <Recipes />
        </Route>
        <Route path="/login">
            <AuthPrompt authType="login" />
        </Route>
        <Route path="/register">
            <AuthPrompt authType="register" />
        </Route>
    </main>
</Router>
