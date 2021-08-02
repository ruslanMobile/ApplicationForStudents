package com.example.applicationforstudents.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.ViewModelProvider;

import com.example.applicationforstudents.Architecture.ViewModelMy;
import com.example.applicationforstudents.R;
import com.example.applicationforstudents.Room.Subject;
import com.example.applicationforstudents.SQLite.Constants;
import com.example.applicationforstudents.SQLite.DataBaseManager;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CustomBottomSheet extends BottomSheetDialogFragment {
    long idEl = -1;
    AutoCompleteTextView autoCompleteTextView;
    EditText editTextTime, editTextSubject, editTextTeacher, editTextType, editTextAudience, editTextNote;
    Drawable mDrawable;
    Button cancel, save,delete;
    ImageButton close;
    TextView textNameOfDay, errorSubject, errorTime, errorAudience;
    //DataBaseManager dataBaseManager;
    Bundle bundle;
    ISetListViewListener iSetListViewListener;
    Cursor cursor;

    ViewModelMy viewModelMy;
    Subject subject;
    public interface ISetListViewListener {
        void setData();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            iSetListViewListener = (ISetListViewListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+" need to impliment BottomSheetListener interface.");
        }
    }

    //Встановлення стилізації CustomBottomSheet
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Ініціалізація елементів
        bundle = getArguments();

        subject = (Subject) bundle.getSerializable("subject");

        View view = inflater.inflate(R.layout.bottom_sheet, container, false);
        textNameOfDay = view.findViewById(R.id.textNameOfDayBottomSheet);
        textNameOfDay.setText(bundle.getString("dayWithMonth"));
        mDrawable = getActivity().getResources().getDrawable(R.drawable.ic_clock);
        autoCompleteTextView = view.findViewById(R.id.autoCompleteTextView);
        cancel = view.findViewById(R.id.buttonCancelBottomSheet);
        cancel.setOnClickListener(cancelListener);
        close = view.findViewById(R.id.imageButtonCloseBottomSheet);
        close.setOnClickListener(cancelListener);
        save = view.findViewById(R.id.buttonSaveBottomSheet);
        save.setOnClickListener(saveListener);
        editTextTime = view.findViewById(R.id.editTextTimeBottomSheet);
        editTextTime.addTextChangedListener(editTextTimeWatcher);

        editTextSubject = view.findViewById(R.id.editTextSubjectBottomSheet);
        editTextTeacher = view.findViewById(R.id.editTextTeacherBottomSheet);
        editTextType = view.findViewById(R.id.autoCompleteTextView);
        editTextAudience = view.findViewById(R.id.editTextAudiceBottomSheet);
        editTextNote = view.findViewById(R.id.editTextNoteBottomSheet);
        errorSubject = view.findViewById(R.id.textSubjectErrorBottomSheet);
        errorTime = view.findViewById(R.id.textTimeErrorBottomSheet);
        errorAudience = view.findViewById(R.id.textAudiceErrorBottomSheet);
        delete = view.findViewById(R.id.buttonDeleteBottomSheet);
        delete.setOnClickListener(deleteElementListener);

        viewModelMy = new ViewModelProvider(this).get(ViewModelMy.class);

        //
        if(bundle.getLong("id") != -1)
            idEl = bundle.getLong("id");

        Log.d("MyLog","id" + idEl);
        if(idEl >= 0) {
           /* dataBaseManager = new DataBaseManager(getContext());
            dataBaseManager.open();
            cursor = dataBaseManager.getCursorForId(idEl+1);
            cursor.moveToFirst();
            editTextSubject.setText(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_NAME_SUBJECT)));
            editTextTime.setText(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_TIME)));
            editTextAudience.setText(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_AUDIENCE)));
            editTextTeacher.setText(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_TEACHER)));
            editTextType.setText(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_TYPE_SUBJECT)));
            editTextNote.setText(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_NOTE)));
            dataBaseManager.close();*/

            //subject = viewModelMy.getElementForId(idEl+1);
            editTextSubject.setText(subject.getName());
            editTextTime.setText(subject.getTime());
            editTextAudience.setText(subject.getAudience());
            editTextTeacher.setText(subject.getTeacher());
            editTextType.setText(subject.getType());
            editTextNote.setText(subject.getNote());
        }else delete.setVisibility(View.GONE);
        //

        //Адаптер для Input з вибором типу пари
        String arrayLesson[] = getResources().getStringArray(R.array.typeOfLessonArray);
        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), R.layout.choice_type_of_lesson, arrayLesson);
        autoCompleteTextView.setAdapter(arrayAdapter);

        getDialog().setOnShowListener(DialogOnShowListener);
        return view;
    }


    //Слухач для вибору часу пари, створює шаблон для правильного вибору проміжку дати і міняє колір іконки в залежності чи введений текст
    TextWatcher editTextTimeWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.d("MyLog", "onTextChanged " + s + start + before + count);
            char[] str = editTextTime.getText().toString().toCharArray();
            if (start == 2 && before == 0 && count == 1) {
                editTextTime.setText(str[0] + "" + str[1] + ":" + str[2]);
                editTextTime.setSelection(4);
            } else if (start == 5 && before == 0 && count == 1) {
                editTextTime.setText(str[0] + "" + str[1] + "" + str[2] + "" + str[3] + "" + str[4] + "-" + str[5]);
                editTextTime.setSelection(7);
            } else if (start == 8 && before == 0 && count == 1) {
                editTextTime.setText(str[0] + "" + str[1] + "" + str[2] + "" + str[3] + "" + str[4] + "" + str[5] + "" + str[6] + "" + str[7] + ":" + str[8]);
                editTextTime.setSelection(10);
            }
            Log.d("MyLog", new String(str));

            if (editTextTime.length() >= 1) {
                mDrawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.textDay), PorterDuff.Mode.MULTIPLY));
            } else if (editTextTime.length() == 0) {
                mDrawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.textDarkGreyOpacity), PorterDuff.Mode.MULTIPLY));
            }
            editTextTime.setCompoundDrawablesWithIntrinsicBounds(mDrawable, null, null, null);
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };


    //Слухач виходу з BottomSheet
    View.OnClickListener cancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
        }
    };


    //Слухач збереження данних в БД
    View.OnClickListener saveListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (textCheckError()) {
                /*dataBaseManager = new DataBaseManager(getContext());
                dataBaseManager.open();*/
                if(idEl == -1) {
                    /*dataBaseManager.insert(editTextSubject.getText().toString(), editTextTeacher.getText().toString(), editTextType.getText().toString(),
                            editTextTime.getText().toString(), editTextAudience.getText().toString(), editTextNote.getText().toString(), bundle.getString("fullDate"));*/
                    viewModelMy.insert(new Subject(editTextSubject.getText().toString(), editTextTeacher.getText().toString(), editTextType.getText().toString(),
                            editTextTime.getText().toString(), editTextAudience.getText().toString(), editTextNote.getText().toString(), bundle.getString("fullDate")));

                }else{
                   /* dataBaseManager.upDate(editTextSubject.getText().toString(), editTextTeacher.getText().toString(), editTextType.getText().toString(),
                            editTextTime.getText().toString(), editTextAudience.getText().toString(), editTextNote.getText().toString(), bundle.getString("fullDate"),idEl+1);*/
                    viewModelMy.upDate(subject);
                }
                //dataBaseManager.close();
                iSetListViewListener.setData();
                dismiss();
            }
        }
    };

    View.OnClickListener deleteElementListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            /*dataBaseManager = new DataBaseManager(getContext());
            dataBaseManager.open();
            dataBaseManager.deleteForId(idEl+1);
            dataBaseManager.close();*/
            viewModelMy.deleteForId(subject);
            iSetListViewListener.setData();
            dismiss();
        }
    };

    //Перевірка чи введені поля EditText з обов'язковим введенням
    public boolean textCheckError() {
        if (editTextSubject.getText().toString().length() < 1)
            errorSubject.setVisibility(View.VISIBLE);
        else errorSubject.setVisibility(View.GONE);

        if (editTextTime.getText().toString().length() != 11) errorTime.setVisibility(View.VISIBLE);
        else errorTime.setVisibility(View.GONE);

        if (editTextAudience.getText().toString().length() < 1)
            errorAudience.setVisibility(View.VISIBLE);
        else errorAudience.setVisibility(View.GONE);

        if (editTextSubject.getText().toString().length() < 1 || editTextTime.getText().toString().length() != 11 ||
                editTextAudience.getText().toString().length() < 1) return false;
        else return true;
    }


    //Встановлення висоти для BottomSheetDialog на весь екран
    DialogInterface.OnShowListener DialogOnShowListener = new DialogInterface.OnShowListener() {
        @Override
        public void onShow(DialogInterface dialog) {
            BottomSheetDialog d = (BottomSheetDialog) dialog;
            FrameLayout bottomSheet = (FrameLayout) d.findViewById(R.id.design_bottom_sheet);
            CoordinatorLayout coordinatorLayout = (CoordinatorLayout) bottomSheet.getParent();
            BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
            bottomSheetBehavior.setPeekHeight(bottomSheet.getHeight());
            coordinatorLayout.getParent().requestLayout();
        }
    };

}
