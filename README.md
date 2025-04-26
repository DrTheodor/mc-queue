# Queue Mod
A FabricMC library mod, that allows you to queue tasks and split them into steps that run over multiple of ticks.

## Example Usage

### Getting started

Add the jitpack maven repository:
```groovy
maven {
    url "https://theo.is-a.dev/maven-repo/"
}
```

Add the dependency:
```groovy
include(modImplementation("com.github.DrTheodor:mc-queue:v1.0.0"))
include(modImplementation("com.github.DrTheodor:mc-scheduler:v1.0.1"))
```

- `ActionQueue` - allows to queue sync and async tasks. Example usage:
```java
ActionQueue queue = new ActionQueue();

queue.thenRun(() -> System.out.println("Running a synchronous task."))
        .thenRun(f -> {
            System.out.println("Running an 'async' task");
            f.finish(); // when our async task is finished, make sure to 'finish' it.
        }).firstRun(() -> System.out.println("But first, this will run."));

Value<Integer> ctx = new Value<>(0);

queue.thenRunSteps(() -> {
    System.out.println("Current value: " + ctx.value);
    return ctx.value++ == 100;
}, TimeUnit.TICKS, 2, 20);

queue.execute(); // run the queued tasks.
```

### Utility
- `ChunkEraser` - erases chunks. Example usage:
```java
ChunkEraser eraser = new ChunkEraser.Builder()
        .every(TimeUnit.TICKS, 1).withMaxTime(20)
        .withFlags(Block.FORCE_STATE).loadChunks(true)
        .build();

eraser.erase().execute();
```

- `BlockQueue` - queues `#setBlockState` calls. Example usage:
```java
ActionQueue queue = new ActionQueue();
BlockQueue block = new BlockQueue.Simple();

block.set(new BlockPos(0, 100, 0), Blocks.AIR.getDefaultState(), Block.FORCE_STATE);
block.set(new BlockPos(0, 0, 0), Blocks.DIAMOND_BLOCK.getDefaultState());

block.schedulePlace(block, world, TimeUnit.TICKS, 2, 20)
    .thenRun(() -> System.out.println("Finished placing queued blocks!"))
        .execute();
```

- `QueuedStructureTemplate` - a `StructureTemplate` which queues its tasks for maximum performance. Example usage:
```java
StructureTemplate template = ...;

new QueuedStructureTemplate(template).place(world, pos, pivot, placementData, random, flags)
    .ifPresentOrElse(queue -> queue.thenRun(
            () -> System.out.println("Finished placing a queued structure!")),
            () -> System.out.println("Failed to place a queued structure!")
    ).ifPresent(ActionQueue::execute);
```

### How to turn a for-cycle into steps:
```java
for (int i = 0; i < 100; i++) {
    // stuff
}

// is equivalent to

ActionQueue queue = new ActionQueue();
Value<Integer> i = new Value<>(0);

queue.thenRunSteps(() -> {
    // stuff
    return !(i++ < 100);
}, TimeUnit.TICKS, 2, 20);

queue.execute();
```
