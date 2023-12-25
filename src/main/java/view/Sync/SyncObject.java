package view.Sync;

public class SyncObject {

    private boolean dateSelected = false;

    public synchronized void setDateSelected(boolean selected) {
        this.dateSelected = selected;
        notifyAll();
    }

    public synchronized boolean isDateSelected() {
        return dateSelected;
    }

    public synchronized void waitForDateSelection() throws InterruptedException {
        while (!dateSelected) {
            System.out.println("Waiting");
            wait();
        }
    }
}
