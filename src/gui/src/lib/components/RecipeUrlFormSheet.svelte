<script lang="ts">
    import * as Sheet from "$lib/components/ui/sheet";
    import { Button } from "$lib/components/ui/button";
    import { Label } from "$lib/components/ui/label";
    import { Input } from "$lib/components/ui/input";

    export let open: boolean;

    let url = "";
</script>

<Sheet.Root bind:open>
    <Sheet.Content side="right">
        <Sheet.Header class="text-left">
            <Sheet.Title>Add recipe</Sheet.Title>
            <Sheet.Description>
                Add the url of the recipe you want to save here. Click add when
                you are done.
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
                <Button builders={[builder]} type="submit">Add</Button>
            </Sheet.Close>
        </Sheet.Footer>
    </Sheet.Content>
</Sheet.Root>
