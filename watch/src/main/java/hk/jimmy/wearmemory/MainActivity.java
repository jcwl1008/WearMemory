package hk.jimmy.wearmemory;
import android.app.*; import android.content.*; import android.os.*; import android.graphics.Color; import android.view.*; import android.widget.*; import java.io.*; import java.text.*; import java.util.*;
public class MainActivity extends Activity {
public void onCreate(Bundle b){super.onCreate(b);requestPermissions(new String[]{"android.permission.RECORD_AUDIO","android.permission.POST_NOTIFICATIONS"},1);show();}
void show(){LinearLayout l=Ui.page(this,"聲音記錄");Ui.text(this,l,"開始後可返回錶面",15);Ui.button(this,l,"開始錄音",()->{if(checkSelfPermission("android.permission.RECORD_AUDIO")!=0)return;startForegroundService(new Intent(this,RecordService.class));});Ui.button(this,l,"停止錄音",()->stopService(new Intent(this,RecordService.class)));Ui.button(this,l,"同步錄音",()->RecordService.sync(this));Ui.text(this,l,"每 5 分鐘儲存\n等待同步："+Ui.dir(this,"audio").list().length+" 段",14);}}
