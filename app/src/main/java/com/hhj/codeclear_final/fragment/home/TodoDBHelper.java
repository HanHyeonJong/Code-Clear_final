package com.hhj.codeclear_final.fragment.home;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class TodoDBHelper extends SQLiteOpenHelper {

    // DB명, DB 버전
    public static final String DATABASE_NAME = "TodoModel.db";
    private static final int DATABASE_VERSION = 1;

    //테이블 항목
    public static final String TABLE_NAME = "TodoModel";
    public static final String TABLE_ID = "id";
    public static final String COL_NAME = "content";
    public static final String COL_DATE = "date";
    public static final String COL_CHECKED = "checked";
//    public static final String COL_FEELING = "feeling";

    public TodoDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_NAME + " TEXT NOT NULL, "
                + COL_DATE + " TEXT NOT NULL, "
                + COL_CHECKED + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 현재 존재하는 테이블 삭제
        String sql = "DROP TABLE IF EXISTS " + TABLE_ID;

        // 테이블 다시 만들기
        db.execSQL(sql);
    }

    //리스트 1건 추가
    public int insert(String content, String date, int checked) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues row = new ContentValues();
        row.put("content", content);
        row.put("date", date);
        row.put("checked", checked);

        long rowId = db.insert("TodoModel", null, row);

        db.close();

        if (rowId != -1) {
            return 1;
        }else {
            return -1;
        }
    }

    //리스트 날짜별로 전체 조회
    public ArrayList<TodoModel> todoRegDate(String date) {
        SQLiteDatabase database = getReadableDatabase();

        //select 명령 실행
        Cursor cursor = database.query(TABLE_NAME, new String[]{TABLE_ID, COL_NAME, COL_DATE, COL_CHECKED}, COL_DATE + " = ?", new String[]{date}, null, null, null, null);

        //select 명령 결과 저장
        ArrayList<TodoModel> todoModel = todoModelFromCursor(cursor);

        Log.d("TAG", todoModel.toString());
        cursor.close();
        database.close();
        return todoModel;
    }

    //리스트 공통값 정리
    ArrayList<TodoModel> todoModelFromCursor(Cursor cursor) {
        ArrayList<TodoModel> todoModel = new ArrayList<>();

        while(cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String content = cursor.getString(1);
            String date = cursor.getString(2);
            int checked = cursor.getInt(3);

            TodoModel todoModel1 = new TodoModel(id, content, date, checked);

            todoModel.add(todoModel1);
        }
        return todoModel;
    }

    //리스트 수정
    void upDate(String content, int checked) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues row = new ContentValues();

        row.put("checked", checked);

        int n = database.update("TodoModel", row,  "content = ?", new String[]{content});
        if( n == 1) {
            Log.d("TAG", content + "컬럼을 " + checked + " 로 변경함");
        }else{
            Log.d("TAG", content + "컬럼을 " + checked + " 로 변경실패");
        }
        database.close();
    }

    //리스트 삭제
    int delete(TodoModel todoModel) {
        SQLiteDatabase database = getWritableDatabase();

        int n = database.delete("TodoModel", "content = ?", new String[]{todoModel.getContent()});

        database.close();

        return n;
    }
}
