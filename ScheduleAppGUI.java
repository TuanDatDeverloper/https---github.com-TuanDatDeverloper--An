import javax.swing.*;
import java.awt.*;
import java.util.TimeZone;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Calendar;
import java.util.Date;
import java.time.LocalDate; // Add missing import for LocalDate

public class ScheduleAppGUI extends JFrame {
    // Đảm bảo các biến được khai báo ở phạm vi lớp để có thể sử dụng trong các phương thức khác
    private JComboBox<String> repeatComboBox;
    private JSpinner reminderSpinner;

    public ScheduleAppGUI() {
        setTitle("Tên App");
        setSize(1440, 960);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        // ------------------------- Thanh MENU -------------------------
        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(CommonConstants.SECONDARY_COLOR);
        menuPanel.setPreferredSize(new Dimension(240, 960));
        menuPanel.setLayout(null);

        // Panel Side Bar
        JPanel sideBarPanel = new JPanel();
        sideBarPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        sideBarPanel.setBackground(CommonConstants.SECONDARY_COLOR);
        sideBarPanel.setBounds(10, 20, 80, 75);
        sideBarPanel.setLayout(null);
        // icon
        ImageIcon sideBar = new ImageIcon("C:\\Users\\This PC\\IdeaProjects\\scheduleApplication\\src\\main\\img\\side_bar.png");
        JLabel sideBarIcon = new JLabel(sideBar);
        sideBarIcon.setBounds(0, 5, 80, 65);

        sideBarPanel.add(sideBarIcon);
        menuPanel.add(sideBarPanel);

        // Thêm Mini Calendar vào menuPanel
        MiniCalendarPanel miniCalendarPanel = new MiniCalendarPanel();
        miniCalendarPanel.setBounds(0, 115, 240, 200);
        menuPanel.add(miniCalendarPanel);

        // Panel Lịch Trình
        JPanel schedulePanel = new JPanel();
        schedulePanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        schedulePanel.setBackground(CommonConstants.TERTIARY_COLOR);
        schedulePanel.setBounds(10, 360, 220, 70);
        schedulePanel.setLayout(null);
        // chữ
        JLabel scheduleLabel = new JLabel("Lịch trình");
        scheduleLabel.setForeground(CommonConstants.PRIMARY_COLOR);
        scheduleLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        scheduleLabel.setBounds(100, 10, 100, 50);
        // icon
        ImageIcon schedule = new ImageIcon("C:\\Users\\This PC\\IdeaProjects\\scheduleApplication\\src\\main\\img\\calendar.png");
        JLabel scheduleIcon = new JLabel(schedule);
        scheduleIcon.setBounds(5, 5, 80, 60);

        schedulePanel.add(scheduleLabel);
        schedulePanel.add(scheduleIcon);
        menuPanel.add(schedulePanel);

        // Panel Tin Nhắn
        JPanel messagePanel = new JPanel();
        messagePanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        messagePanel.setBackground(CommonConstants.TERTIARY_COLOR);
        messagePanel.setBounds(10, 460, 220, 70);
        messagePanel.setLayout(null);
        // chữ
        JLabel messageLabel = new JLabel("Tin nhắn");
        messageLabel.setForeground(CommonConstants.PRIMARY_COLOR);
        messageLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        messageLabel.setBounds(100, 10, 100, 50);
        // icon
        ImageIcon message = new ImageIcon("C:\\Users\\This PC\\IdeaProjects\\scheduleApplication\\src\\main\\img\\speech_bubble.png");
        JLabel messageIcon = new JLabel(message);
        messageIcon.setBounds(5, 5, 80, 60);

        messagePanel.add(messageLabel);
        messagePanel.add(messageIcon);
        menuPanel.add(messagePanel);

        // Panel Profile User
        JPanel profilePanel = new JPanel();
        profilePanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        profilePanel.setBackground(CommonConstants.SECONDARY_COLOR);
        profilePanel.setBounds(10, 830, 80, 75);
        profilePanel.setLayout(null);
        // icon
        ImageIcon profile = new ImageIcon("C:\\Users\\This PC\\IdeaProjects\\scheduleApplication\\src\\main\\img\\profile_user.png");
        JLabel profileIcon = new JLabel(profile);
        profileIcon.setBounds(0, 5, 80, 65);

        profilePanel.add(profileIcon);
        menuPanel.add(profilePanel);
        add(menuPanel, BorderLayout.WEST);
        // ==================================================================

        // ------------------------- Phần Nội Dung LỊCH TRÌNH -------------------------
        JPanel scheduleContentPanel = new JPanel();
        scheduleContentPanel.setBackground(CommonConstants.PRIMARY_COLOR);
        scheduleContentPanel.setPreferredSize(new Dimension(890, 960));
        scheduleContentPanel.setLayout(null);

        // Search Panel
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(null);
        searchPanel.setBounds(20, 20, 200, 30);
        searchPanel.setBackground(Color.WHITE);

        // TextField tìm kiếm
        JTextField searchField = new JTextField("Search...");
        searchField.setForeground(Color.GRAY);
        searchField.setBounds(40, 5, 350, 30);
        searchField.setBorder(null);

        // Placeholder xử lý
        searchField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (searchField.getText().equals("Search...")) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (searchField.getText().isEmpty()) {
                    searchField.setForeground(Color.GRAY);
                    searchField.setText("Search...");
                }
            }
        });
        searchPanel.add(searchField);

        // Add a search button next to the search field
        JButton searchButton = new JButton("Search");
        searchButton.setBounds(400, 5, 80, 30);
        searchButton.setBackground(CommonConstants.TEXT_COLOR);
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);
        searchButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = searchField.getText();
                EventDAO eventDAO = new EventDAO();
                List<Event> results = eventDAO.searchEvents(query);

                StringBuilder resultText = new StringBuilder("Search Results:\n\n");
                for (Event event : results) {
                    resultText.append("- ").append(event.getTitle())
                             .append(" (" + event.getStartTime() + " - " + event.getEndTime() + ")\n")
                             .append("  Location: " + event.getLocation() + "\n")
                             .append("  Description: " + event.getDescription() + "\n\n");
                }

                JOptionPane.showMessageDialog(null, resultText.toString(), "Search Results", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        searchPanel.add(searchButton);

        // Month Panel
        JPanel monthPanel = new JPanel();
        monthPanel.setBounds(230, 20, 80, 30);
        monthPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        monthPanel.setBackground(CommonConstants.TEXT_COLOR);

        // Label Month
        JLabel monthLabel = new JLabel("Month");
        monthLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        monthLabel.setForeground(Color.white);
        monthPanel.add(monthLabel);

        // Icon mũi tên xuống
        ImageIcon arrowDownIcon = new ImageIcon("src/main/img/down-arrow 24px.png");
        JLabel arrowDownLabel = new JLabel(arrowDownIcon);
        monthPanel.add(arrowDownLabel);

        // Today Panel
        JPanel todayPanel = new JPanel();
        todayPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));
        todayPanel.setBounds(340, 20, 80, 30);
        todayPanel.setBackground(CommonConstants.TEXT_COLOR);

        JLabel todayLabel = new JLabel("Today");
        todayLabel.setForeground(Color.white);
        todayLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        todayPanel.add(todayLabel);

        scheduleContentPanel.add(searchPanel);
        scheduleContentPanel.add(monthPanel);
        scheduleContentPanel.add(todayPanel);

        // Load events into the calendar
        BigCalendarPanel calendarPanel = new BigCalendarPanel();
        calendarPanel.setBounds(2, 70, 870, 850);
        calendarPanel.loadEvents();
        scheduleContentPanel.add(calendarPanel);

        add(scheduleContentPanel, BorderLayout.CENTER);
        // ==================================================================

//        // ------------------------- Phần Nội Dung TIN NHẮN -------------------------
//        JPanel MessageContentPanel = new JPanel();
//        MessageContentPanel.setBackground(CommonConstants.PRIMARY_COLOR);
//        MessageContentPanel.setPreferredSize(new Dimension(890, 960));
//        add(MessageContentPanel, BorderLayout.CENTER);
//        // ==================================================================

        // ------------------------- Thanh CRUD -------------------------
        JPanel eventDetailPanel = new JPanel();
        eventDetailPanel.setBackground(CommonConstants.SECONDARY_COLOR);
        eventDetailPanel.setPreferredSize(new Dimension(310, 960));
        eventDetailPanel.setLayout(null);

        // ==== Title ====
        JTextField titleField = new JTextField("Event");
        titleField.setBounds(20, 20, 270, 30); // Điều chỉnh chiều rộng của titleField
        titleField.setFont(new Font("Segoe UI", Font.BOLD, 18));
        eventDetailPanel.add(titleField);

        // ==== Time (Start and End Time, Editable) ====
        // Start Date & Time
        JLabel startTimeLabel = new JLabel("Start Time:");
        startTimeLabel.setForeground(Color.WHITE);
        startTimeLabel.setBounds(20, 70, 270, 20);
        eventDetailPanel.add(startTimeLabel);

        // Date spinner for start date
        SpinnerDateModel startDateModel = new SpinnerDateModel();
        JSpinner startDateSpinner = new JSpinner(startDateModel);
        JSpinner.DateEditor startDateEditor = new JSpinner.DateEditor(startDateSpinner, "MMM dd, yyyy");
        startDateSpinner.setEditor(startDateEditor);
        startDateSpinner.setBounds(20, 100, 130, 25);
        eventDetailPanel.add(startDateSpinner);

        // Time spinner for start time
        SpinnerDateModel startTimeModel = new SpinnerDateModel();
        JSpinner startTimeSpinner = new JSpinner(startTimeModel);
        JSpinner.DateEditor startTimeEditor = new JSpinner.DateEditor(startTimeSpinner, "hh:mm a");
        startTimeSpinner.setEditor(startTimeEditor);
        startTimeSpinner.setBounds(160, 100, 130, 25);
        eventDetailPanel.add(startTimeSpinner);

        // End Date & Time
        JLabel endTimeLabel = new JLabel("End Time:");
        endTimeLabel.setForeground(Color.WHITE);
        endTimeLabel.setBounds(20, 140, 270, 20);
        eventDetailPanel.add(endTimeLabel);

        // Date spinner for end date
        SpinnerDateModel endDateModel = new SpinnerDateModel();
        JSpinner endDateSpinner = new JSpinner(endDateModel);
        JSpinner.DateEditor endDateEditor = new JSpinner.DateEditor(endDateSpinner, "MMM dd, yyyy");
        endDateSpinner.setEditor(endDateEditor);
        endDateSpinner.setBounds(20, 170, 130, 25);
        eventDetailPanel.add(endDateSpinner);

        // Time spinner for end time
        SpinnerDateModel endTimeModel = new SpinnerDateModel();
        JSpinner endTimeSpinner = new JSpinner(endTimeModel);
        JSpinner.DateEditor endTimeEditor = new JSpinner.DateEditor(endTimeSpinner, "hh:mm a");
        endTimeSpinner.setEditor(endTimeEditor);
        endTimeSpinner.setBounds(160, 170, 130, 25);
        eventDetailPanel.add(endTimeSpinner);

        // ==== All-day ====
        JCheckBox allDayCheck = new JCheckBox("All-day");
        allDayCheck.setForeground(Color.WHITE);
        allDayCheck.setBackground(CommonConstants.SECONDARY_COLOR);
        allDayCheck.setBounds(20, 210, 100, 20);
        allDayCheck.addActionListener(_ -> {
            boolean isAllDay = allDayCheck.isSelected();
            startTimeSpinner.setEnabled(!isAllDay);
            endTimeSpinner.setEnabled(!isAllDay);

            if (isAllDay) {
                // Set default times for all-day events
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.set(Calendar.MINUTE, 0);
                startTimeModel.setValue(cal.getTime());

                cal.set(Calendar.HOUR_OF_DAY, 23);
                cal.set(Calendar.MINUTE, 59);
                endTimeModel.setValue(cal.getTime());
            }
        });
        eventDetailPanel.add(allDayCheck);

        // ==== Timezone (Selection) ====
        JLabel timezoneLabel = new JLabel("Timezone:");
        timezoneLabel.setForeground(Color.WHITE);
        timezoneLabel.setBounds(20, 240, 270, 20);
        eventDetailPanel.add(timezoneLabel);

        String[] timezones = TimeZone.getAvailableIDs();
        JComboBox<String> timezoneComboBox = new JComboBox<>(timezones);
        timezoneComboBox.setBounds(20, 270, 270, 25);
        eventDetailPanel.add(timezoneComboBox);

        // ==== Repeat ====
        JButton repeatBtn = new JButton("Repeat");
        repeatBtn.setBounds(20, 310, 100, 25);
        eventDetailPanel.add(repeatBtn);

        // ==== Location ====
        JTextField locationField = new JTextField("Location");
        locationField.setBounds(20, 350, 270, 25);
        eventDetailPanel.add(locationField);

        // ==== Docs & Links ====
        JTextField linkField = new JTextField("Docs and links");
        linkField.setBounds(20, 390, 270, 25);
        eventDetailPanel.add(linkField);

        // ==== Description ====
        JTextArea descArea = new JTextArea("Description");
        descArea.setBounds(20, 430, 270, 80);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        eventDetailPanel.add(descArea);

        // ==== Reminder (ComboBox for selection) ====
        JLabel reminderLabel = new JLabel("Reminders:");
        reminderLabel.setForeground(Color.WHITE);
        reminderLabel.setBounds(20, 520, 270, 20);
        eventDetailPanel.add(reminderLabel);

        String[] reminderOptions = {"None", "5 min before", "10 min before", "30 min before", "1 hour before"};
        JComboBox<String> reminderComboBox = new JComboBox<>(reminderOptions);
        reminderComboBox.setBounds(20, 550, 270, 25);
        eventDetailPanel.add(reminderComboBox);

        // ==== Confirm Button (Save Event to Database) ===
        JButton confirmBtn = new JButton("Xác nhận");
        confirmBtn.setBounds(20, 590, 270, 30);
        confirmBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText();
                String location = locationField.getText();
                String description = descArea.getText();
                String repeatType = (String) repeatComboBox.getSelectedItem();
                Integer reminderTime = (Integer) reminderSpinner.getValue();

                try {
                    // Get dates and times from spinners
                    Date startDate = (Date) startDateSpinner.getValue();
                    Date startTimeValue = (Date) startTimeSpinner.getValue();
                    Date endDate = (Date) endDateSpinner.getValue();
                    Date endTimeValue = (Date) endTimeSpinner.getValue();

                    // Combine date and time
                    Calendar startCalendar = Calendar.getInstance();
                    startCalendar.setTime(startDate);

                    Calendar startTimeCal = Calendar.getInstance();
                    startTimeCal.setTime(startTimeValue);

                    startCalendar.set(Calendar.HOUR_OF_DAY, startTimeCal.get(Calendar.HOUR_OF_DAY));
                    startCalendar.set(Calendar.MINUTE, startTimeCal.get(Calendar.MINUTE));

                    Calendar endCalendar = Calendar.getInstance();
                    endCalendar.setTime(endDate);

                    Calendar endTimeCal = Calendar.getInstance();
                    endTimeCal.setTime(endTimeValue);

                    endCalendar.set(Calendar.HOUR_OF_DAY, endTimeCal.get(Calendar.HOUR_OF_DAY));
                    endCalendar.set(Calendar.MINUTE, endTimeCal.get(Calendar.MINUTE));

                    // Create timestamps
                    java.sql.Timestamp startTimestamp = new java.sql.Timestamp(startCalendar.getTimeInMillis());
                    java.sql.Timestamp endTimestamp = new java.sql.Timestamp(endCalendar.getTimeInMillis());

                    // Save to database
                    EventDAO eventDAO = new EventDAO();
                    eventDAO.addEvent(title, startTimestamp, endTimestamp, description, location, repeatType, reminderTime);

                    JOptionPane.showMessageDialog(null, "Lịch trình đã được lưu thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);

                    // Refresh calendar to show new event
                    calendarPanel.updateCalendar();
                    calendarPanel.loadEvents();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi khi lưu lịch trình: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace(); // Print stack trace for debugging
                }
            }
        });
        eventDetailPanel.add(confirmBtn);

        // Add delete button functionality
        JButton deleteBtn = new JButton("Xóa");
        deleteBtn.setBounds(20, 630, 270, 30);
        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String title = titleField.getText();
                    EventDAO eventDAO = new EventDAO();
                    eventDAO.deleteEventByTitle(title);

                    JOptionPane.showMessageDialog(null, "Sự kiện đã được xóa thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);

                    // Refresh calendar
                    calendarPanel.updateCalendar();
                    calendarPanel.loadEvents();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi khi xóa sự kiện: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });
        eventDetailPanel.add(deleteBtn);

        // Thêm các thành phần giao diện cho lặp lại sự kiện và nhắc nhở
        JLabel repeatLabel = new JLabel("Repeat:");
        repeatLabel.setForeground(Color.WHITE);
        repeatLabel.setBounds(20, 670, 270, 20);
        eventDetailPanel.add(repeatLabel);

        String[] repeatOptions = {"None", "Daily", "Weekly", "Monthly", "Yearly"};
        repeatComboBox = new JComboBox<>(repeatOptions);
        repeatComboBox.setBounds(20, 700, 270, 25);
        eventDetailPanel.add(repeatComboBox);

        JLabel reminderMinutesLabel = new JLabel("Reminder (minutes before):");
        reminderMinutesLabel.setForeground(Color.WHITE);
        reminderMinutesLabel.setBounds(20, 740, 270, 20);
        eventDetailPanel.add(reminderMinutesLabel);

        reminderSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 1440, 1)); // Tối đa 1440 phút (24 giờ)
        reminderSpinner.setBounds(20, 770, 270, 25);
        eventDetailPanel.add(reminderSpinner);

        // Cập nhật logic nút xác nhận để lưu thông tin lặp lại và nhắc nhở
        confirmBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText();
                String location = locationField.getText();
                String description = descArea.getText();
                String repeatType = (String) repeatComboBox.getSelectedItem();
                Integer reminderTime = (Integer) reminderSpinner.getValue();

                try {
                    // Get dates and times from spinners
                    Date startDate = (Date) startDateSpinner.getValue();
                    Date startTimeValue = (Date) startTimeSpinner.getValue();
                    Date endDate = (Date) endDateSpinner.getValue();
                    Date endTimeValue = (Date) endTimeSpinner.getValue();

                    // Combine date and time
                    Calendar startCalendar = Calendar.getInstance();
                    startCalendar.setTime(startDate);

                    Calendar startTimeCal = Calendar.getInstance();
                    startTimeCal.setTime(startTimeValue);

                    startCalendar.set(Calendar.HOUR_OF_DAY, startTimeCal.get(Calendar.HOUR_OF_DAY));
                    startCalendar.set(Calendar.MINUTE, startTimeCal.get(Calendar.MINUTE));

                    Calendar endCalendar = Calendar.getInstance();
                    endCalendar.setTime(endDate);

                    Calendar endTimeCal = Calendar.getInstance();
                    endTimeCal.setTime(endTimeValue);

                    endCalendar.set(Calendar.HOUR_OF_DAY, endTimeCal.get(Calendar.HOUR_OF_DAY));
                    endCalendar.set(Calendar.MINUTE, endTimeCal.get(Calendar.MINUTE));

                    // Create timestamps
                    java.sql.Timestamp startTimestamp = new java.sql.Timestamp(startCalendar.getTimeInMillis());
                    java.sql.Timestamp endTimestamp = new java.sql.Timestamp(endCalendar.getTimeInMillis());

                    // Save to database
                    EventDAO eventDAO = new EventDAO();
                    eventDAO.addEvent(title, startTimestamp, endTimestamp, description, location, repeatType, reminderTime);

                    JOptionPane.showMessageDialog(null, "Lịch trình đã được lưu thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);

                    // Refresh calendar to show new event
                    calendarPanel.updateCalendar();
                    calendarPanel.loadEvents();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi khi lưu lịch trình: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace(); // Print stack trace for debugging
                }
            }
        });

        // Thêm sự kiện cho nút "Lịch trình"
        schedulePanel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EventDAO eventDAO = new EventDAO();
                List<Event> events = eventDAO.getAllEvents();

                JPanel scheduleDetailsPanel = new JPanel();
                scheduleDetailsPanel.setLayout(new BoxLayout(scheduleDetailsPanel, BoxLayout.Y_AXIS));

                for (Event event : events) {
                    JLabel eventLabel = new JLabel("- " + event.getTitle() + " (" + event.getStartTime() + " - " + event.getEndTime() + ")");
                    eventLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
                    scheduleDetailsPanel.add(eventLabel);
                }

                JScrollPane scrollPane = new JScrollPane(scheduleDetailsPanel);
                scrollPane.setPreferredSize(new Dimension(400, 300));

                JOptionPane.showMessageDialog(null, scrollPane, "Detailed Schedule", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Thêm sự kiện cho nút "Profile User"
        profilePanel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                String userInfo = "Thông tin người dùng:\n\n" +
                                  "Họ tên: Nguyễn Văn A\n" +
                                  "Email: nguyenvana@example.com\n" +
                                  "Số điện thoại: 0123456789";

                JOptionPane.showMessageDialog(null, userInfo, "Thông tin người dùng", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Add search functionality
        searchField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = searchField.getText();
                EventDAO eventDAO = new EventDAO();
                List<Event> results = eventDAO.searchEvents(query);

                StringBuilder resultText = new StringBuilder("Kết quả tìm kiếm:\n\n");
                for (Event event : results) {
                    resultText.append("- ").append(event.getTitle())
                             .append(" (" + event.getStartTime() + " - " + event.getEndTime() + ")\n")
                             .append("  Địa điểm: " + event.getLocation() + "\n")
                             .append("  Mô tả: " + event.getDescription() + "\n\n");
                }

                JOptionPane.showMessageDialog(null, resultText.toString(), "Kết quả tìm kiếm", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Add functionality for "Month" button
        monthPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
                String selectedMonth = (String) JOptionPane.showInputDialog(null, "Chọn tháng:", "Tháng", JOptionPane.QUESTION_MESSAGE, null, months, months[LocalDate.now().getMonthValue() - 1]);

                if (selectedMonth != null) {
                    int monthIndex = java.util.Arrays.asList(months).indexOf(selectedMonth) + 1;
                    calendarPanel.setMonth(monthIndex);
                }
            }
        });

        // Add functionality for "Today" button
        todayPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                calendarPanel.setToday();
            }
        });
    }
}
