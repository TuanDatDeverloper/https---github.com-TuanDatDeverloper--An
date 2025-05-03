public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            ScheduleAppGUI app = new ScheduleAppGUI();
            app.setVisible(true);
        });
    }
}