## Issue with micronaut + graalvm and jGit

Commands used to create the repo:
```bash
$ mn -V      
Micronaut Version: 3.5.2

$ mn create-cli-app --jdk 17 -l java -t junit -b gradle -f graalvm  cli-app-latest
```

Commands used to build the app jar:
```bash
$ ./gradlew build
```

Tool execution using the jar:
```bash
$ java -jar build/libs/cli-app-latest-0.1-all.jar
23:06:14.485 [main] INFO  i.m.context.env.DefaultEnvironment - Established active environments: [cli]
Cloned repo to /tmp/git-clone/micronaut-core/.git
```


Commands used to build the native binary:
```bash
$ ./gradlew nativeCompile
```

GraalVM used is version `22.0.0.2 Java 17 CE`.
Tool execution produces `java.lang.NoSuchMethodException` exception.
```bash
$ build/native/nativeCompile/cli-app-latest
22:43:12.291 [main] INFO  i.m.context.env.DefaultEnvironment - Established active environments: [cli]
Exception in thread "main" java.lang.Error: java.lang.NoSuchMethodException: org.eclipse.jgit.internal.JGitText.<init>()
        at org.eclipse.jgit.nls.GlobalBundleCache.lookupBundle(GlobalBundleCache.java:70)
        at org.eclipse.jgit.nls.NLS.get(NLS.java:125)
        at org.eclipse.jgit.nls.NLS.getBundleFor(NLS.java:101)
        at org.eclipse.jgit.internal.JGitText.get(JGitText.java:28)
        at org.eclipse.jgit.lib.Config.allValuesOf(Config.java:380)
        at org.eclipse.jgit.lib.Config.getEnum(Config.java:368)
        at org.eclipse.jgit.internal.storage.file.FileRepository.create(FileRepository.java:231)
        at org.eclipse.jgit.api.InitCommand.call(InitCommand.java:103)
        at org.eclipse.jgit.api.CloneCommand.init(CloneCommand.java:267)
        at org.eclipse.jgit.api.CloneCommand.call(CloneCommand.java:173)
        at cli.app.latest.CliAppLatestCommand.clone(CliAppLatestCommand.java:53)
        at cli.app.latest.CliAppLatestCommand.run(CliAppLatestCommand.java:36)
        at picocli.CommandLine.executeUserObject(CommandLine.java:1939)
        at picocli.CommandLine.access$1300(CommandLine.java:145)
        at picocli.CommandLine$RunLast.executeUserObjectOfLastSubcommandWithSameParent(CommandLine.java:2358)
        at picocli.CommandLine$RunLast.handle(CommandLine.java:2352)
        at picocli.CommandLine$RunLast.handle(CommandLine.java:2314)
        at picocli.CommandLine$AbstractParseResultHandler.execute(CommandLine.java:2179)
        at picocli.CommandLine$RunLast.execute(CommandLine.java:2316)
        at picocli.CommandLine.execute(CommandLine.java:2078)
        at io.micronaut.configuration.picocli.PicocliRunner.run(PicocliRunner.java:137)
        at io.micronaut.configuration.picocli.PicocliRunner.run(PicocliRunner.java:114)
        at cli.app.latest.CliAppLatestCommand.main(CliAppLatestCommand.java:28)
Caused by: java.lang.NoSuchMethodException: org.eclipse.jgit.internal.JGitText.<init>()
        at java.lang.Class.getConstructor0(DynamicHub.java:3585)
        at java.lang.Class.getDeclaredConstructor(DynamicHub.java:2754)
        at org.eclipse.jgit.nls.GlobalBundleCache.lookupBundle(GlobalBundleCache.java:63)
        ... 22 more
```
