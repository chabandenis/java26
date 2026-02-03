package ru.otus.hw;

/**
 * Результаты тестов
 */
public class InfoResults {
    private int passed;
    private int failed;

    public void incPassed() {
        this.passed++;
    }

    public void incFailed() {
        this.failed++;
    }

    public InfoResults() {
        this.failed = 0;
        this.passed = 0;
    }

    public String getResultInfo() {
        return "Result {" + "passed=" + passed + ", failed=" + failed + ", total=" + (failed + passed) + '}';
    }
}
