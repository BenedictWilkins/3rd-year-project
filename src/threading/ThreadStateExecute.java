package threading;

public class ThreadStateExecute implements ThreadState {
	@Override
	public void run(AgentRunnable runnable) {
		runnable.getAgent().execute(null);
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}