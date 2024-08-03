<script lang="ts">
  import Moon from "lucide-svelte/icons/moon";
  import Sun from "lucide-svelte/icons/sun";
  import { CookingPot, PlusIcon } from "lucide-svelte";
  import { ModeWatcher, toggleMode, mode } from "mode-watcher";
  import { Switch } from "$lib/components/ui/switch";
  import Recipes from "$lib/components/Recipes.svelte";
  import Button from "$lib/components/ui/button/button.svelte";
  import * as Sheet from "$lib/components/ui/sheet";
  import { Input } from "$lib/components/ui/input";
  import { Label } from "$lib/components/ui/label";

  let url = "";
  let open = false;
  let numSuccess = 0;

  async function handleSubmit(): Promise<void> {
    let response = await fetch("/api/recipe", {
      method: "POST",
      headers: { "content-type": "application/json" },

      body: JSON.stringify({ url }),
    });
    if (response.status === 200) {
      numSuccess += 1;
    }
  }
</script>

<ModeWatcher />
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
    <div class="flex items-center gap-4">
      <div class="flex items-center gap-2">
        {#if $mode === "dark"}
          <Moon />
        {:else}
          <Sun />
        {/if}
        <Switch onCheckedChange={toggleMode} />
      </div>
      <Button
        variant="outline"
        on:click={() => (open = true)}
        class="px-2.5 !py-0"><PlusIcon class="p-0" /></Button
      >
    </div>
  </div>
</header>
<main class="max-w-screen-xl w-[80%] mx-auto py-4">
  {#key numSuccess}
    <Recipes />
  {/key}
</main>

<Sheet.Root bind:open>
  <Sheet.Content side="right">
    <Sheet.Header class="text-left">
      <Sheet.Title>Add recipe</Sheet.Title>
      <Sheet.Description>
        Add the url of the recipe you want to save here. Click add when you are
        done.
      </Sheet.Description>
    </Sheet.Header>
    <div class="grid gap-4 py-4">
      <div class="grid grid-cols-4 items-center gap-4">
        <Label for="url" class="text-right">Url</Label>
        <Input id="url" bind:value={url} class="col-span-3" />
      </div>
    </div>
    <Sheet.Footer>
      <Sheet.Close asChild let:builder>
        <Button builders={[builder]} type="submit" on:click={handleSubmit}
          >Add</Button
        >
      </Sheet.Close>
    </Sheet.Footer>
  </Sheet.Content>
</Sheet.Root>
