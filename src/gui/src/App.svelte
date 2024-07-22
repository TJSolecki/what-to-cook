<script lang="ts">
  import Moon from "lucide-svelte/icons/moon";
  import { CookingPot } from "lucide-svelte";
  import { ModeWatcher, toggleMode } from "mode-watcher";
  import { Switch } from "$lib/components/ui/switch";
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
    <div class="flex items-center gap-2">
      <Moon />
      <Switch onCheckedChange={toggleMode} />
    </div>
  </div>
</header>
