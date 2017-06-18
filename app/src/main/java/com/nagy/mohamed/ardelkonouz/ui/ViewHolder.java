package com.nagy.mohamed.ardelkonouz.ui;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nagy.mohamed.ardelkonouz.R;

/**
 * Created by mohamednagy on 6/18/2017.
 */
public class ViewHolder {

    public static class MainScreenViewHolder {

        public final LinearLayout CHILD_LIST_LAYOUT;
        public final LinearLayout INSTRUCTOR_LIST_LAYOUT;
        public final LinearLayout EMPLOYEE_LIST_LAYOUT;
        public final LinearLayout COURSES_LIST_LAYOUT;
        public final LinearLayout UPDATE_ONLINE_LAYOUT;
        public final LinearLayout SALARY_LIST_LAYOUT;

        public MainScreenViewHolder(View mainScreenView) {
            CHILD_LIST_LAYOUT = (LinearLayout) mainScreenView.findViewById(R.id.child_list_layout_view);
            INSTRUCTOR_LIST_LAYOUT = (LinearLayout) mainScreenView.findViewById(R.id.instructor_list_layout_view);
            EMPLOYEE_LIST_LAYOUT = (LinearLayout) mainScreenView.findViewById(R.id.employee_list_layout_view);
            COURSES_LIST_LAYOUT = (LinearLayout) mainScreenView.findViewById(R.id.course_list_layout_view);
            UPDATE_ONLINE_LAYOUT = (LinearLayout) mainScreenView.findViewById(R.id.child_update_online_layout_view);
            SALARY_LIST_LAYOUT = (LinearLayout) mainScreenView.findViewById(R.id.salary_layout_view);
        }
    }

    public static class ChildInputScreenViewHolder{

        public final EditText CHILD_NAME_EDIT_TEXT;
        public final EditText CHILD_AGE_EDIT_TEXT;
        public final TextView FIRST_ORDER_TEXT_VIEW;
        public final TextView MIDDLE_ORDER_TEXT_VIEW;
        public final TextView LAST_ORDER_TEXT_VIEW;
        public final TextView MALE_GENDER_TEXT_VIEW;
        public final TextView FEMALE_GENDER_TEXT_VIEW;
        public final EditText MOTHER_NAME_EDIT_TEXT;
        public final EditText MOTHER_MOBILE_EDIT_TEXT;
        public final EditText MOTHER_JOB_EDIT_TEXT;
        public final EditText MOTHER_QUALIFICATION_EDIT_TEXT;
        public final EditText FATHER_NAME_EDIT_TEXT;
        public final EditText FATHER_MOBILE_EDIT_TEXT;
        public final EditText WHATSAPP_EDIT_TEXT;
        public final EditText FATHER_JOB_EDIT_TEXT;
        public final TextView ARABIC_GOVERNMENTAL_TEXT_VIEW;
        public final TextView ENGLISH_GOVERNMENTAL_TEXT_VIEW;
        public final TextView ARABIC_PRIVATE_TEXT_VIEW;
        public final TextView ENGLISH_PRIVATE_TEXT_VIEW;
        public final TextView NONE_EDUCATION_TEXT_VIEW;
        public final TextView PRIMARY_EDUCATION_TEXT_VIEW;
        public final TextView PREPARATORY_TEXT_VIEW;
        public final TextView SECONDARY_EDUCATION_TEXT_VIEW;
        public final TextView NONE_YEAR_TEXT_VIEW;
        public final TextView FIRST_YEAR_TEXT_VIEW;
        public final TextView SECOND_YEAR_TEXT_VIEW;
        public final TextView THIRD_YEAR_TEXT_VIEW;
        public final TextView FORTH_YEAR_TEXT_VIEW;
        public final TextView FIFTH_YEAR_TEXT_VIEW;
        public final TextView SIXTH_YEAR_TEXT_VIEW;
        public final TextView GOOD_SPEAKER_TEXT_VIEW;
        public final TextView SOCIAL_TEXT_VIEW;
        public final TextView LEADING_TEXT_VIEW;
        public final TextView NEURAL_TEXT_VIEW;
        public final TextView COLLABORATOR_TEXT_VIEW;
        public final TextView ASKS_FOR_HELP_TEXT_VIEW;
        public final TextView WORRIES_TEXT_VIEW;
        public final TextView LEAVES_PROBLEMS_TEXT_VIEW;
        public final TextView TRIES_SOLVE_TEXT_VIEW;
        public final TextView DRAWING_TEXT_VIEW;
        public final TextView ELECTRONIC_TEXT_VIEW;
        public final TextView WATCHING_TV_TEXT_VIEW;
        public final TextView HANDWORK_TEXT_VIEW;
        public final Button SUBMIT_CHILD_BUTTON;

        public ChildInputScreenViewHolder(View childInputView){

            CHILD_NAME_EDIT_TEXT = (EditText) childInputView.findViewById(R.id.child_input_name_edit_view);
            CHILD_AGE_EDIT_TEXT = (EditText) childInputView.findViewById(R.id.child_input_age_edit_view);
            FIRST_ORDER_TEXT_VIEW = (TextView) childInputView.findViewById(R.id.child_input_first_order_text_view);
            MIDDLE_ORDER_TEXT_VIEW = (TextView) childInputView.findViewById(R.id.child_input_middle_order_text_view);
            LAST_ORDER_TEXT_VIEW = (TextView) childInputView.findViewById(R.id.child_input_last_order_text_view);
            MALE_GENDER_TEXT_VIEW = (TextView) childInputView.findViewById(R.id.child_input_male_gender_text_view);
            FEMALE_GENDER_TEXT_VIEW = (TextView) childInputView.findViewById(R.id.child_input_female_gender_text_view);
            MOTHER_NAME_EDIT_TEXT = (EditText) childInputView.findViewById(R.id.child_input_mother_name_edit_view);
            MOTHER_MOBILE_EDIT_TEXT = (EditText) childInputView.findViewById(R.id.child_input_mother_mobile_edit_view);
            MOTHER_JOB_EDIT_TEXT = (EditText) childInputView.findViewById(R.id.child_input_mother_jop_edit_view);
            MOTHER_QUALIFICATION_EDIT_TEXT = (EditText) childInputView.findViewById(R.id.child_input_mother_qualification_edit_view);
            FATHER_NAME_EDIT_TEXT = (EditText) childInputView.findViewById(R.id.child_input_father_name_edit_view);
            FATHER_MOBILE_EDIT_TEXT = (EditText) childInputView.findViewById(R.id.child_input_father_mobile_edit_view);
            WHATSAPP_EDIT_TEXT = (EditText) childInputView.findViewById(R.id.child_input_father_whatsApp_edit_view);
            FATHER_JOB_EDIT_TEXT = (EditText) childInputView.findViewById(R.id.child_input_father_job_edit_view);
            ARABIC_GOVERNMENTAL_TEXT_VIEW = (TextView) childInputView.findViewById(R.id.child_input_government_arabic_text_view);
            ENGLISH_GOVERNMENTAL_TEXT_VIEW = (TextView) childInputView.findViewById(R.id.child_input_government_english_text_view);
            ARABIC_PRIVATE_TEXT_VIEW = (TextView) childInputView.findViewById(R.id.child_input_private_arabic_text_view);
            ENGLISH_PRIVATE_TEXT_VIEW = (TextView) childInputView.findViewById(R.id.child_input_private_english_text_view);
            NONE_EDUCATION_TEXT_VIEW = (TextView) childInputView.findViewById(R.id.child_input_none_academic_text_view);
            PRIMARY_EDUCATION_TEXT_VIEW = (TextView) childInputView.findViewById(R.id.child_input_primary_academic_text_view);
            PREPARATORY_TEXT_VIEW = (TextView) childInputView.findViewById(R.id.child_input_preparatory_academic_text_view);
            SECONDARY_EDUCATION_TEXT_VIEW = (TextView) childInputView.findViewById(R.id.child_input_secondary_academic_text_view);
            NONE_YEAR_TEXT_VIEW = (TextView) childInputView.findViewById(R.id.child_input_none_year_text_view);
            FIRST_YEAR_TEXT_VIEW = (TextView) childInputView.findViewById(R.id.child_input_first_year_text_view);
            SECOND_YEAR_TEXT_VIEW = (TextView) childInputView.findViewById(R.id.child_input_second_year_text_view);
            THIRD_YEAR_TEXT_VIEW = (TextView) childInputView.findViewById(R.id.child_input_third_year_text_view);
            FORTH_YEAR_TEXT_VIEW = (TextView) childInputView.findViewById(R.id.child_input_forth_year_text_view);
            FIFTH_YEAR_TEXT_VIEW = (TextView) childInputView.findViewById(R.id.child_input_fifth_year_text_view);
            SIXTH_YEAR_TEXT_VIEW = (TextView) childInputView.findViewById(R.id.child_input_sixth_year_text_view);
            GOOD_SPEAKER_TEXT_VIEW = (TextView) childInputView.findViewById(R.id.child_input_good_speaker_text_view);
            SOCIAL_TEXT_VIEW = (TextView) childInputView.findViewById(R.id.child_input_social_text_view);
            LEADING_TEXT_VIEW = (TextView) childInputView.findViewById(R.id.child_input_leading_text_view);
            NEURAL_TEXT_VIEW = (TextView) childInputView.findViewById(R.id.child_input_neural_text_view);
            COLLABORATOR_TEXT_VIEW = (TextView) childInputView.findViewById(R.id.child_input_collaborator_text_view);
            ASKS_FOR_HELP_TEXT_VIEW = (TextView) childInputView.findViewById(R.id.child_input_ans1_problem_text_view);
            WORRIES_TEXT_VIEW = (TextView) childInputView.findViewById(R.id.child_input_ans2_problem_text_view);
            LEAVES_PROBLEMS_TEXT_VIEW = (TextView) childInputView.findViewById(R.id.child_input_ans3_problem_text_view);
            TRIES_SOLVE_TEXT_VIEW = (TextView) childInputView.findViewById(R.id.child_input_ans4_problem_text_view);
            DRAWING_TEXT_VIEW = (TextView) childInputView.findViewById(R.id.child_input_ans1_time_text_view);
            ELECTRONIC_TEXT_VIEW = (TextView) childInputView.findViewById(R.id.child_input_ans2_time_text_view);
            WATCHING_TV_TEXT_VIEW = (TextView) childInputView.findViewById(R.id.child_input_ans3_time_text_view);
            HANDWORK_TEXT_VIEW = (TextView) childInputView.findViewById(R.id.child_input_ans4_time_text_view);
            SUBMIT_CHILD_BUTTON = (Button) childInputView.findViewById(R.id.child_input_submit_application_button);
        }

    }

}
