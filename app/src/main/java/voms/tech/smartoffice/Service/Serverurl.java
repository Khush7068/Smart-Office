package voms.tech.smartoffice.Service;


/**
 * Created by SGT-09 on 5/24/2016.
 */
public class Serverurl {

    private static String Server_Address = "http://13.233.14.22:8185/smartoffice/first/customer/";
    public static String Image_Address = "https://login.topologypro.one/";
    public static String Image_Support = "https://support.topologypro.one/";

    public static String Login = Server_Address + "login.php?";
    public static String Forget = Server_Address + "forgetPassword?";
    public static String register = Server_Address + "register.php?";
    public static String dashboard = Server_Address + "dashboard.php?";
    public static String user_profile = Server_Address + "profileDetails?";
    public static String changePassword = Server_Address + "change_password.php?";
    public static String get_profile = Server_Address + "getprofile.php?";
    public static String update_profile = Server_Address + "update_profile.php?";
    public static String issue_report = Server_Address + "raiseissue.php";
    public static String issue_list = Server_Address + "issues_list.php";
    public static String issue_log = Server_Address + "issue_log_list.php";
    public static String DeviceToken = Server_Address + "device_master.php";
}
