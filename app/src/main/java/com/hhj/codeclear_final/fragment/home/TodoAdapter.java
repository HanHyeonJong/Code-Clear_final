package com.hhj.codeclear_final.fragment.home;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.hhj.codeclear_final.R;

import java.util.ArrayList;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {
    // 원본 데이터
    private ArrayList<TodoModel> todoModelArrayList = new ArrayList<TodoModel>();
    private TodoDBHelper todoDBHelper;



    @NonNull
    @Override
    public TodoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //레이아웃 구하면서 전개
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todomodel, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        TodoModel todoModel = todoModelArrayList.get(position);
        holder.todoCheckbox.setText(todoModel.getContent());
        holder.pos = position;                                      //바인딩 될때 원본에서의 데이터 순서 설정

        //checkbox 상태 변경
        holder.todoCheckbox.setChecked(todoModel.getChecked() == 1 ? true : false);
    }

    @Override
    public int getItemCount() {
        return todoModelArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        int pos;                //바인딩 될때 원본에서의 데이터 순서 설정
        CheckBox todoCheckbox;  //체크박스

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //체크박스 - 클릭했을때 checked 값 바꾸기
            todoCheckbox = itemView.findViewById(R.id.todoCheckbox);
            todoCheckbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //원본 데이타 구하기
                    TodoModel todoModel = todoModelArrayList.get(pos);
                    todoDBHelper = new TodoDBHelper(v.getContext());

                    //checked 값 확인해서 변경
                    if (todoModel.getChecked() == 0) {
                        todoModel.setChecked(1);
                        todoDBHelper.upDate(todoModel.getContent(), 1);
                    }else if (todoModel.getChecked() == 1) {
                        todoModel.setChecked(0);
                        todoDBHelper.upDate(todoModel.getContent(), 0);
                    }
                }
            });

            todoCheckbox.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    //커스텀 대화상자 화면구성 전개
                    View deleteView = View.inflate(v.getContext(), R.layout.toast_todo_delete, null);

                    //삭제 UI (아니요, 네)
                    Button btnNo = deleteView.findViewById(R.id.btnNo);
                    Button btnYes = deleteView.findViewById(R.id.btnYes);

                    //전개한 화면구성을 대화상자에 설정
                    builder.setView(deleteView);
                    builder.setCancelable(false);

                    AlertDialog alertDialog = builder.create();

                    //아니요 버튼
                    btnNo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });

                    //네 버튼
                    btnYes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //삭제할 원본 데이타 구하기
                            TodoModel todoModel = todoModelArrayList.get(pos);
                            todoDBHelper = new TodoDBHelper(view.getContext());
                            int n = todoDBHelper.delete(todoModel);

                            if( n == 1){
                                //db에서 삭제되었으므로 방법1) Adapter의 데이타를 TABLE에서 전체를 다시읽어온다.
                                //                  방법2) Adapter의 pos번째 데이타만 삭제한다.
                                todoModelArrayList.remove(pos);
                                Log.d("TAG", todoModelArrayList.toString());
                                //Adapter에게 데이타가 변경되었음을 알려준다.
                                TodoAdapter.this.notifyDataSetChanged();

                                Toast.makeText(view.getContext(), "삭제 성공", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(view.getContext(), "삭제 실패", Toast.LENGTH_SHORT).show();
                            }
                            alertDialog.dismiss();
                        }
                    });

                    //다이얼로그 투명하게
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    alertDialog.show();

                    return true;
                }
            });
        }


    }

    //원본데이터 한번에 변경
    public void setTodoModelArrayList(ArrayList<TodoModel> todoModelArrayList) {
        this.todoModelArrayList = todoModelArrayList;
    }

}
