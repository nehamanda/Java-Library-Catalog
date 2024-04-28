import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Copy implements Serializable {
    private int copyNumber;
    private boolean available;
    private Member checkedOutBy; // Member who checked out this copy

    private List<Member> pastCheckouts;

    private List<Member> holds;
    private Date checkedOutDate;
    private Date dueDate; // Due date for this copy

    public Copy(int copyNumber) {
        this.copyNumber = copyNumber;
        this.available = true;
        this.checkedOutBy = null;
        this.checkedOutDate = null;
        this.dueDate = null;
        this.pastCheckouts = new ArrayList<Member>();
        this.holds = new ArrayList<Member>();
    }

    // Getter and setter methods


    public List<Member> getHolds() {
        return holds;
    }

    public List<Member> getPastCheckouts() {
        return pastCheckouts;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getCopyNumber() {
        return copyNumber;
    }


    public Member getCheckedOutBy() {
        return checkedOutBy;
    }

    public void setCheckedOutBy(Member checkedOutBy) {
        this.checkedOutBy = checkedOutBy;
    }

    public Date getCheckedOutDate() {
        return checkedOutDate;
    }

    public void setCheckedOutDate(Date checkedOutDate) {
        this.checkedOutDate = checkedOutDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    // Override equals and hashCode methods

    // Common methods for all types of items
}
