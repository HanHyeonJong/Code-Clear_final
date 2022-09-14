package com.hhj.codeclear_final.fragment.home;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.hhj.codeclear_final.R;
import com.hhj.codeclear_final.databinding.FragmentHomeBinding;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

public class HomeFragment extends Fragment implements View.OnClickListener{
    // 뷰 바인딩
    private FragmentHomeBinding binding;

    // 키보드 내리기
    InputMethodManager imm;

    //달력 관련
    LocalDate localDate;

    //어뎁터
    TodoAdapter todoAdapter;
    TodoDBHelper todoDBHelper;

    //기분체크 관련
    ImageView ivFeeling;
    ImageView feel_1, feel_2, feel_3, feel_4, feel_5;

    //원본 데이터
    ArrayList<TodoModel> todoModelArrayList = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        //키보드 내리기
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        // 클릭 리스너
        binding.btnPlus.setOnClickListener(this);
        binding.btnBack.setOnClickListener(this);
        binding.tvDate.setOnClickListener(this);
        binding.btnNext.setOnClickListener(this);
        binding.ivFeeling.setOnClickListener(this);

        //현재 날짜 설정
        localDate = LocalDate.now();
        binding.tvDate.setText(monthYearFromDate(localDate));

        //db 생성
        todoDBHelper = new TodoDBHelper(getContext());

        //리사이클러뷰
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.todoRecyclerView.setLayoutManager(linearLayoutManager);

        //어뎁터 초기화
        todoAdapter = new TodoAdapter();

        //리사이클러뷰에 어뎁터 연결
        binding.todoRecyclerView.setAdapter(todoAdapter);

        //DB에 있는 값 가져오기
        //특정 날짜만 가져오기
        todoModelArrayList = todoDBHelper.todoRegDate(binding.tvDate.getText().toString());
        todoAdapter.setTodoModelArrayList(todoModelArrayList);
        todoAdapter.notifyDataSetChanged();

        return view;
    }


    //날짜 타입 설정
    private String monthYearFromDate(LocalDate date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
        return date.format(dateTimeFormatter);
    }

    //todoData 추가 했을때 뜨는 Toast
    public void toDoAddToast(String text) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_todo_add, getActivity().findViewById(R.id.toastLayout));

        TextView toastMsg = layout.findViewById(R.id.toastTV);
        toastMsg.setText(text);

        Toast customToast = new Toast(getActivity());
        //토스트 위치 설정
        customToast.setGravity(Gravity.CENTER, 0, 0);
        customToast.setDuration(Toast.LENGTH_SHORT);
        customToast.setView(layout);

        //토스트 보여주기
        customToast.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //추가 버튼
            case R.id.btnPlus: {
                if (binding.etTodoInput.length() < 1) {
                    //추가 할 체크 리스트가 없어요! toast 띄우기
                    toDoAddToast("오늘 할 일을 입력 후 눌려주세요!");
                    return;
                }

                // 값 저장
                String data = binding.etTodoInput.getText().toString();
                String date = binding.tvDate.getText().toString();
                int checked = 0;

                // 다음 입력을 위한 EditText 초기화
                binding.etTodoInput.setText("");

                int n = todoDBHelper.insert(data, date, checked);

                Log.d("TAG", String.valueOf(n));

                todoAdapter.setTodoModelArrayList(todoDBHelper.todoRegDate(date));
                todoAdapter.notifyDataSetChanged();

                //키보드 내리기
                imm.hideSoftInputFromWindow(requireView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                if (n > 0) {
                    toDoAddToast("체크 리스트가 추가되었습니다!");
                } else {
                    toDoAddToast("체크 리스트 추가에 실패했습니다!");
                }
                break;
            }

            //상단 달력
            case R.id.tvDate: {
                //현재 날짜 받아오기
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        year = datePicker.getYear();
                        month = datePicker.getMonth();
                        dayOfMonth = datePicker.getDayOfMonth();

                        Calendar c = Calendar.getInstance();
                        c.set(year, month, dayOfMonth);

                        //날짜 형식 바꿔주기
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        String strDate = format.format(c.getTime());

                        //날짜 지정
                        binding.tvDate.setText(strDate);

                        //날짜별로 todolist 값을 가져온다.
                        todoAdapter.setTodoModelArrayList(todoDBHelper.todoRegDate(binding.tvDate.getText().toString()));
                        todoAdapter.notifyDataSetChanged();
                    }
                }, mYear, mMonth, mDay);

                datePickerDialog.setCancelable(false);
                datePickerDialog.show();
                break;
            }

            //날짜 -1 버튼
            case R.id.btnBack: {
                //현재 일-1 변수 담기
                localDate = localDate.minusDays(1);
                binding.tvDate.setText(monthYearFromDate(localDate));

                //날짜별로 todolist 값을 가져온다.
                todoAdapter.setTodoModelArrayList(todoDBHelper.todoRegDate(binding.tvDate.getText().toString()));
                todoAdapter.notifyDataSetChanged();
                break;
            }

            //날짜 +1 버튼
            case R.id.btnNext: {
                //현재 일+1 변수 담기
                localDate = localDate.plusDays(1);
                binding.tvDate.setText(monthYearFromDate(localDate));

                //날짜별로 todolist 값을 가져온다.
                todoAdapter.setTodoModelArrayList(todoDBHelper.todoRegDate(binding.tvDate.getText().toString()));
                todoAdapter.notifyDataSetChanged();
                break;
            }

            // 기분 설정
            case R.id.ivFeeling: {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                //커스텀 대화상자 전개
                View dialogView = View.inflate(getContext(), R.layout.feeling_input, null);

                //기분체크 UI
                feel_1 = dialogView.findViewById(R.id.feel_1);
                feel_2 = dialogView.findViewById(R.id.feel_2);
                feel_3 = dialogView.findViewById(R.id.feel_3);
                feel_4 = dialogView.findViewById(R.id.feel_4);
                feel_5 = dialogView.findViewById(R.id.feel_5);

                //커스텀 대화상자 설정
                builder.setView(dialogView);
                builder.setCancelable(true);

                AlertDialog alertDialog = builder.create();

                //클릭시 뒷배경 회색으로 되는거 없애기
                alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                alertDialog.show();

                //기분체크 이미지 변경
                feel_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ivFeeling.setImageResource(R.drawable.fragment_feeling_1);
                        alertDialog.dismiss();
                    }
                });
                feel_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ivFeeling.setImageResource(R.drawable.fragment_feeling_2);
                        alertDialog.dismiss();
                    }
                });
                feel_3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ivFeeling.setImageResource(R.drawable.fragment_feeling_3);
                        alertDialog.dismiss();
                    }
                });
                feel_4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ivFeeling.setImageResource(R.drawable.fragment_feeling_4);
                        alertDialog.dismiss();
                    }
                });
                feel_5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ivFeeling.setImageResource(R.drawable.fragment_feeling_5);
                        alertDialog.dismiss();
                    }
                });
                break;
            }
        }
    }

    //뷰 바인딩 때문에
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}