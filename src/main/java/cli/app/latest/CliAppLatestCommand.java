package cli.app.latest;

import io.micronaut.configuration.picocli.PicocliRunner;
import io.micronaut.context.ApplicationContext;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.SshTransport;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.nio.file.FileSystems;
import java.util.Objects;

@Command(name = "cli-app-latest", description = "...",
        mixinStandardHelpOptions = true)
public class CliAppLatestCommand implements Runnable {

    @Option(names = {"-v", "--verbose"}, description = "...")
    boolean verbose;

    public static void main(String[] args) throws Exception {
        PicocliRunner.run(CliAppLatestCommand.class, args);
    }

    public void run() {
        // business logic here
        if (verbose) {
            System.out.println("Hi!");
        } else {
            try(final Git git = clone("https://github.com/micronaut-projects/micronaut-core.git", "/tmp/git-clone/micronaut-core")) {
                System.out.println("Cloned repo to " + git.getRepository().getDirectory().toString());
            } catch (GitAPIException e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected Git clone(String repositoryURI, String cloneDirectory) throws GitAPIException {
        String pathToClone = cloneDirectory + FileSystems.getDefault().getSeparator();

        final CloneCommand cloneCommand = Git.cloneRepository();
        cloneCommand.setURI(repositoryURI);
        cloneCommand.setDirectory(new File(pathToClone));
        cloneCommand.setCloneAllBranches(false);
        cloneCommand.setNoTags();

        return cloneCommand.call();
    }
}
