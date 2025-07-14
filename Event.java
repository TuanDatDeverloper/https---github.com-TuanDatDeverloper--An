import java.sql.Timestamp;

public class Event {
    private int id;
    private String title;
    private Timestamp startTime;
    private Timestamp endTime;
    private String description;
    private String location;
    private Timestamp createdAt;
    private String repeatType; // Loại lặp lại: none, daily, weekly, monthly, yearly
    private Integer reminderTime; // Thời gian nhắc nhở trước sự kiện (phút)

    public Event(int id, String title, Timestamp startTime, Timestamp endTime, String description, String location, Timestamp createdAt, String repeatType, Integer reminderTime) {
        this.id = id;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.location = location;
        this.createdAt = createdAt;
        this.repeatType = repeatType;
        this.reminderTime = reminderTime;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getRepeatType() {
        return repeatType;
    }

    public void setRepeatType(String repeatType) {
        this.repeatType = repeatType;
    }

    public Integer getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(Integer reminderTime) {
        this.reminderTime = reminderTime;
    }
}