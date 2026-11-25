import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;

class Result {
    final String fileName;
    final boolean success;
    final int score;
    final String message;

    Result(String fileName, boolean success, int score, String message) {
        this.fileName = fileName;
        this.success = success;
        this.score = score;
        this.message = message;
    }
}

class ScoreTask implements Callable<Result> {
    private final Path file;

    ScoreTask(Path file) {
        this.file = file;
    }

    public Result call() {
        try {
            String value = Files.readString(file).trim();
            int score = Integer.parseInt(value);

            if (score < 0 || score > 100)
                throw new IllegalArgumentException("Score must be 0 to 100");

            return new Result(file.getFileName().toString(),
                    true, score, "Processed");
        } catch (IOException | IllegalArgumentException e) {
            return new Result(file.getFileName().toString(),
                    false, 0, e.getMessage());
        }
    }
}

public class Q7_ResultProcessor {
    public static void main(String[] args) throws Exception {
        List<Path> files = new ArrayList<>();

        if (args.length > 0) {
            for (String name : args)
                files.add(Paths.get(name));
        } else {
            // Demo input files
            Files.writeString(Paths.get("score1.txt"), "85");
            Files.writeString(Paths.get("score2.txt"), "92");
            Files.writeString(Paths.get("score3.txt"), "bad-data");
            Files.writeString(Paths.get("score4.txt"), "110");
            files.add(Paths.get("score1.txt"));
            files.add(Paths.get("score2.txt"));
            files.add(Paths.get("score3.txt"));
            files.add(Paths.get("score4.txt"));
            files.add(Paths.get("missing.txt"));
        }

        ExecutorService pool = Executors.newFixedThreadPool(4);
        Queue<Result> results = new ConcurrentLinkedQueue<>();
        List<Future<Result>> futures = new ArrayList<>();

        long start = System.currentTimeMillis();

        try {
            for (Path file : files)
                futures.add(pool.submit(new ScoreTask(file)));

            for (Future<Result> future : futures)
                results.add(future.get());
        } finally {
            pool.shutdown();
        }

        int success = 0, failure = 0;
        for (Result r : results) {
            if (r.success) {
                success++;
                System.out.println(r.fileName + ": Score = " + r.score);
            } else {
                failure++;
                System.out.println(r.fileName + ": Failed - " + r.message);
            }
        }

        System.out.println("Successful files: " + success);
        System.out.println("Failed files: " + failure);
        System.out.println("Time taken: "
                + (System.currentTimeMillis() - start) + " ms");
    }
}

/*
Run without arguments for demo files, or pass file names:
java Q7_ResultProcessor score1.txt score2.txt
*/