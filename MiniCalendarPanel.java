import javax.swing.*;
import java.awt.*;
import java.time.*;
import java.time.temporal.WeekFields;

public class MiniCalendarPanel extends JPanel {
    private JLabel monthYearLabel;
    private JPanel calendarGrid;
    private LocalDate currentDate;

    public MiniCalendarPanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(250, 270)); // rộng hơn để chứa số tuần
        setBackground(CommonConstants.SECONDARY_COLOR);

        currentDate = LocalDate.now();

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(CommonConstants.SECONDARY_COLOR);

        JButton prevButton = new JButton("<");
        prevButton.setFocusPainted(false);
        prevButton.setBorderPainted(false);
        prevButton.setContentAreaFilled(false);
        prevButton.setForeground(Color.WHITE);
        prevButton.addActionListener(e -> {
            System.out.println("Previous button clicked: " + e.getActionCommand());
            currentDate = currentDate.minusMonths(1);
            updateCalendar();
        });

        JButton nextButton = new JButton(">");
        nextButton.setFocusPainted(false);
        nextButton.setBorderPainted(false);
        nextButton.setContentAreaFilled(false);
        nextButton.setForeground(Color.WHITE);
        nextButton.addActionListener(e -> {
            System.out.println("Next button clicked: " + e.getActionCommand());
            currentDate = currentDate.plusMonths(1);
            updateCalendar();
        });

        monthYearLabel = new JLabel("", SwingConstants.CENTER);
        monthYearLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        monthYearLabel.setForeground(Color.WHITE);

        header.add(prevButton, BorderLayout.WEST);
        header.add(monthYearLabel, BorderLayout.CENTER);
        header.add(nextButton, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        // Calendar grid
        calendarGrid = new JPanel(new GridLayout(7, 8)); // 7 dòng 8 cột
        calendarGrid.setBackground(CommonConstants.SECONDARY_COLOR);
        add(calendarGrid, BorderLayout.CENTER);

        updateCalendar();
    }

    private void updateCalendar() {
        calendarGrid.removeAll();

        // Add header (first row)
        calendarGrid.add(new JLabel("")); // Ô trống đầu tiên cho số tuần

        String[] days = {"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"};
        for (String day : days) {
            JLabel dayLabel = new JLabel(day, SwingConstants.CENTER);
            dayLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
            dayLabel.setForeground(CommonConstants.TERTIARY_COLOR);
            calendarGrid.add(dayLabel);
        }

        YearMonth yearMonth = YearMonth.of(currentDate.getYear(), currentDate.getMonth());
        LocalDate firstOfMonth = currentDate.withDayOfMonth(1);
        int startDay = firstOfMonth.getDayOfWeek().getValue(); // Monday=1, Sunday=7

        if (startDay == 7) startDay = 0; // Sunday=0

        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate today = LocalDate.now();

        int dayCounter = 1;
        boolean started = false;
        for (int week = 0; week < 6; week++) {
            // First column: Week number
            LocalDate firstDayOfWeek = firstOfMonth.plusDays(week * 7L - startDay);
            int weekNumber = firstDayOfWeek.get(WeekFields.ISO.weekOfYear());

            JLabel weekLabel = new JLabel(String.valueOf(weekNumber), SwingConstants.CENTER);
            weekLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
            weekLabel.setForeground(new Color(200, 200, 200));
            calendarGrid.add(weekLabel);

            for (int dayOfWeek = 0; dayOfWeek < 7; dayOfWeek++) {
                if (!started && dayOfWeek == startDay) started = true;

                if (started && dayCounter <= daysInMonth) {
                    LocalDate thisDay = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), dayCounter);
                    JLabel dayLabel = new JLabel(String.valueOf(dayCounter), SwingConstants.CENTER);
                    dayLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
                    dayLabel.setForeground(Color.WHITE);

                    if (thisDay.isEqual(today)) {
                        dayLabel.setOpaque(true);
                        dayLabel.setBackground(Color.RED);
                        dayLabel.setForeground(Color.WHITE);
                    }

                    calendarGrid.add(dayLabel);
                    dayCounter++;
                } else {
                    calendarGrid.add(new JLabel(""));
                }
            }
        }

        monthYearLabel.setText(currentDate.getMonth().toString() + " " + currentDate.getYear());
        revalidate();
        repaint();
    }
}