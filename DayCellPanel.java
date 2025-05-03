import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.YearMonth;

public class DayCellPanel extends JPanel {
    private JLabel dayLabel;
    private JPanel eventsPanel;
    private boolean isSelected;
    private LocalDate date; // Associated date with the panel

    public DayCellPanel(LocalDate date, YearMonth currentMonth) {
        this.date = date; // Initialize the date
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.GRAY));

        setBackground(CommonConstants.PRIMARY_COLOR); // default background

        dayLabel = new JLabel(String.valueOf(date.getDayOfMonth()), SwingConstants.LEFT);
        dayLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        dayLabel.setForeground(date.getMonth().equals(currentMonth.getMonth()) ? Color.WHITE : Color.GRAY);
        add(dayLabel, BorderLayout.NORTH);

        eventsPanel = new JPanel();
        eventsPanel.setLayout(new BoxLayout(eventsPanel, BoxLayout.Y_AXIS));
        eventsPanel.setOpaque(false);
        add(eventsPanel, BorderLayout.CENTER);

        if (date.isEqual(LocalDate.now())) {
            setBackground(Color.RED);
        }
    }

    public void addEvent(String event) {
        JLabel eventLabel = new JLabel(event);
        eventLabel.setFont(new Font("Tahoma", Font.PLAIN, 10));
        eventLabel.setForeground(Color.ORANGE);
        eventsPanel.add(eventLabel);
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
        if (selected) {
            setBackground(Color.BLUE);
        } else {
            setBackground(CommonConstants.PRIMARY_COLOR);
        }
        repaint();
    }

    public boolean isSelected() {
        return isSelected;
    }

    // Add a getDate method to return the associated date
    public LocalDate getDate() {
        return this.date;
    }
}
