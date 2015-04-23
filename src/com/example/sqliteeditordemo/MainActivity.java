package com.example.sqliteeditordemo;

import java.io.DataOutputStream;
import java.io.File;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity {
	public String path ;
	public String pathroot ;
	public String dbfile = "/data/android.zhibo8/databases/zhibo8.db";
	public SQLiteDatabase dateBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("codePath:"+getPackageCodePath());
        upgradeRootPermission(getPackageCodePath());
        
        
        path = Environment.getDataDirectory().getAbsolutePath();
        pathroot= Environment.getRootDirectory().getAbsolutePath();
        Log.d("path", path);
        Log.d("pathroot",pathroot);
        //File dir = new File(pathroot+dbfile);
        File dir = new File(path+dbfile);
        //File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/zhibo8.db");
       System.out.println(dir.getAbsolutePath());
       
       
       if(!dir.exists())
        {
        	Log.e("eee", "文件不存在");
        	return;
        	
        }else
        {
        	//dateBase = SQLiteDatabase.openOrCreateDatabase(dir, null);
        	//Log.d("dateBase",dateBase.toString());
        	Log.e("eee", dir.getAbsolutePath()+"文件存在");
        }
        dateBase = SQLiteDatabase.openDatabase(dir.getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);
       // System.out.println(dateBase.getSyncedTables());
        //查询所有表名
        //Cursor cursor = dateBase.rawQuery("select name from sqlite_master where type='table' order by name", null);
        Cursor cursor = dateBase.rawQuery("PRAGMA table_info(zhibo8_Team)", null);
       /* if(cursor.moveToFirst())
        {
        	//int id=cursor.getColumnIndex("locale");
        	String locale = cursor.getString(cursor.getColumnIndex("locale"));
        	System.out.println("id:"+cursor.getColumnIndex("locale"));
        	System.out.println("locale:"+locale);
        	System.out.println("-1行"+cursor.getType(0));
        	
        }*/
        cursor.moveToFirst();
        for( int i = 0;!cursor.isLast();i++)
        {
        	System.out.println(cursor.getString(1));
        	cursor.moveToNext();
        }
        
        System.out.println(cursor.toString());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    
    /** 
     * 应用程序运行命令获取 Root权限，设备必须已破解(获得ROOT权限) 
     *  
     * @return 应用程序是/否获取Root权限 
     */  
    public static boolean upgradeRootPermission(String pkgCodePath) {  
        Process process = null;  
        DataOutputStream os = null;  
        try {  
            String cmd="chmod 777 " + pkgCodePath;  
            process = Runtime.getRuntime().exec("su"); //切换到root帐号  
            os = new DataOutputStream(process.getOutputStream());  
            os.writeBytes(cmd + "\n");  
            os.writeBytes("exit\n");  
            os.flush();  
            process.waitFor();  
        } catch (Exception e) {  
            return false;  
        } finally {  
            try {  
                if (os != null) {  
                    os.close();  
                }  
                process.destroy();  
            } catch (Exception e) {  
            }  
        }  
        return true;  
    }  
}
