package com.example.applicationforstudents.Fragments;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.applicationforstudents.R;
import com.example.applicationforstudents.Architecture.ViewModelMy;
import com.example.applicationforstudents.Room.Subject;
import com.example.applicationforstudents.SQLite.DataBaseManager;
import com.example.applicationforstudents.SubjectsAdapter;
import com.github.jhonnyx2012.horizontalpicker.DatePickerListener;
import com.github.jhonnyx2012.horizontalpicker.HorizontalPicker;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class FragmentList extends Fragment {
    Calendar calendar = Calendar.getInstance();
    Button buttonAddElement;
    ImageButton datePicker;
    HorizontalPicker horizontalPicker;
    CustomBottomSheet customBottomSheet;
    ViewModelMy myModel;
    TextView textNameOfDay;
    //DataBaseManager dataBaseManager;
    ListView listViewOfDay;
    String fullDate;

    //
    List<Subject> subjectList = new LinkedList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Підключення ViewModel для збереження Calendar при зміні
        myModel = new ViewModelProvider(this).get(ViewModelMy.class);
        myModel.getLiveData().observe(getViewLifecycleOwner(), new Observer<List<com.example.applicationforstudents.Room.Subject>>() {
            @Override
            public void onChanged(List<com.example.applicationforstudents.Room.Subject> subjects) {
                Log.d("MyLog", "+++ Change Live DAta");
                subjectList = subjects;
                resetListView();
            }
        });
        // myModel.insert(new Subject("Українська моваuu","Олена","Лекція","10:00-11:20","123А","Купити зошит для робіт","2021.08.02"));
        /*
        if(savedInstanceState == null)
            calendar = Calendar.getInstance();
        else
            calendar = myModel.getLiveData().getValue();
            */

        textNameOfDay = view.findViewById(R.id.textNameOfDay);

        datePicker = view.findViewById(R.id.imageButtonCalendar);
        datePicker.setOnClickListener(openDatePicker);

        buttonAddElement = view.findViewById(R.id.buttonAddElement);
        buttonAddElement.setOnClickListener(addElement);

        listViewOfDay = view.findViewById(R.id.listViewOfDay);
        //listViewOfDay.setOnItemClickListener(clickListListener);

        horizontalPicker = (HorizontalPicker) view.findViewById(R.id.datePicker);
        createHorizontalPicker();
    }

    //Слухач для ViewModel
//    Observer<Calendar> observerViewModel = new Observer<Calendar>() {
//        @Override
//        public void onChanged(Calendar calendarNew) {
//            calendar = calendarNew;
//            //Установка назви дня
//            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
//            Date date = new Date();
//            date.setTime(calendar.getTimeInMillis());
//            String dayWeekText = new SimpleDateFormat("EEEE").format(date);
//            textNameOfDay.setText(dayWeekText.substring(0, 1).toUpperCase() + dayWeekText.substring(1));
//
//                /*DateTime dateTime = new DateTime(calendar.getTime());
//                horizontalPicker.setDate(dateTime);*/
//            Log.d("MyLog", "SetDate " + calendar.getTime() + " , " + dayOfWeek + " , " + dayWeekText);
//
//            resetListView();
//        }
//    };


   /* AdapterView.OnItemClickListener clickListListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String selected = ((TextView) view.findViewById(R.id.textViewId)).getText().toString();
            Log.d("MyLog", "id List = " + id + " pos " + position + " " + selected);
            startBottomSheet(Long.valueOf(selected));
        }
    };*/

    public void resetListView() {
        Date date = new Date();
        date.setTime(calendar.getTimeInMillis());
        fullDate = new SimpleDateFormat("yyyy.MM.dd").format(date);
        Log.d("MyLog", "Fulldate" + fullDate);

        List<Subject> changeList = new LinkedList<>();
        for(Subject el:subjectList){
            if(el.getDate().equals(fullDate)){
                changeList.add(el);
            }
        }

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        SubjectsAdapter adapter = new SubjectsAdapter(getContext(), R.layout.item_subject, changeList/*,date,fragmentManager*/);
        listViewOfDay.setAdapter(adapter);
        setListViewHeightBasedOnChildren(listViewOfDay);
        Log.d("MyLog", "================" + calendar.getTime() + " " + subjectList.size());
    }
    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;

        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);

            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth,
                        ViewGroup.LayoutParams.MATCH_PARENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();

        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = (int) (totalHeight + ((listView.getDividerHeight() + (getResources().getDimension(R.dimen.dividerHeight) / 2)) * (listAdapter.getCount())));

        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    //Слухач ImageButton для відкриття календаря
    View.OnClickListener openDatePicker = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new DatePickerDialog(getContext(), dateListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        }
    };
    //Слухач DatePickerDialog для зміни данних в ViewModel
    DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
           /* calendar.set(Calendar.YEAR,year);
            calendar.set(Calendar.MONTH,month);
            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);*/


            /*Calendar calendar1 = Calendar.getInstance();
            calendar1.set(Calendar.YEAR, year);
            calendar1.set(Calendar.MONTH, month);
            calendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);*/
            //myModel.setData(calendar1);

            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            resetListView();
            Log.d("MyLog", calendar.getTime().toString());
        }
    };


    //Створення і редагування горизонтального календаря
    public void createHorizontalPicker() {
        horizontalPicker.setListener(datePickerListener)
                .setDays(60)
                .setOffset(30)
                .setDateSelectedColor(getResources().getColor(R.color.transparent))
                .setDateSelectedTextColor(getResources().getColor(R.color.tabCheck))
                .setMonthAndYearTextColor(getResources().getColor(R.color.transparent))
                .setTodayDateTextColor(getResources().getColor(R.color.black))
                .setTodayDateBackgroundColor(getResources().getColor(R.color.transparent))
                .setDayOfWeekTextColor(getResources().getColor(R.color.textGrey))
                .setUnselectedDayTextColor(getResources().getColor(R.color.textGrey))
                .showTodayButton(false)
                .init();
        horizontalPicker.setBackgroundColor(getResources().getColor(R.color.white));

        DateTime dateTime = new DateTime(calendar.getTime());
        //Log.d("MyLog","dateToday   Year:"+ dateTime.getYearOfCentury() + " month:" + dateTime.getMonthOfYear() + " day" + dateTime.getDayOfMonth());
        horizontalPicker.setDate(dateTime);
    }

    //Слухач зміни дати для горизонтального календаря
    DatePickerListener datePickerListener = new DatePickerListener() {
        @Override
        public void onDateSelected(DateTime dateSelected) {
            //Log.d("MyLog", "================" + dateSelected.toString());
            /*calendar.set(Calendar.YEAR,dateSelected.getYear());
            calendar.set(Calendar.MONTH,dateSelected.getMonthOfYear()-1);
            calendar.set(Calendar.DAY_OF_MONTH,dateSelected.getDayOfMonth());*/
            //myModel.setData(dateSelected);

            /*Calendar calendar1 = Calendar.getInstance();
            calendar1.set(Calendar.YEAR, dateSelected.getYear());
            calendar1.set(Calendar.MONTH, dateSelected.getMonthOfYear() - 1);
            calendar1.set(Calendar.DAY_OF_MONTH, dateSelected.getDayOfMonth());*/
            //  myModel.setData(calendar1);

            calendar.set(Calendar.YEAR, dateSelected.getYear());
            calendar.set(Calendar.MONTH, dateSelected.getMonthOfYear() - 1);
            calendar.set(Calendar.DAY_OF_MONTH, dateSelected.getDayOfMonth());
            resetListView();
        }
    };


    //Слухач Button для відкриття BottomSheet
    View.OnClickListener addElement = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startBottomSheet(-1);
        }
    };

    public void startBottomSheet(long id) {
        Date date = new Date();
        customBottomSheet = new CustomBottomSheet();
        date.setTime(calendar.getTimeInMillis());
        String dayWithMonth = new SimpleDateFormat("EEEE, d MMMM").format(date), fullDate = new SimpleDateFormat("yyyy.MM.dd").format(date);

        Log.d("MyLog", "StartBottomSheet ==== " + calendar.getTime());
        Bundle bundle = new Bundle();
        bundle.putString("fullDate", fullDate);
        bundle.putString("dayWithMonth", dayWithMonth.substring(0, 1).toUpperCase() + dayWithMonth.substring(1));
        bundle.putLong("id", id);
        customBottomSheet.setArguments(bundle);
        customBottomSheet.show(getActivity().getSupportFragmentManager(), "");
    }
}
