CREATE TABLE events (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    description TEXT,
    location VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    repeat_type ENUM('none', 'daily', 'weekly', 'monthly', 'yearly') DEFAULT 'none',
    reminder_time INT DEFAULT NULL -- Thời gian nhắc nhở trước sự kiện (phút)
);