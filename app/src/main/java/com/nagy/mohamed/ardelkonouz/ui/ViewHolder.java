package com.nagy.mohamed.ardelkonouz.ui;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
        public final LinearLayout SALARY_LIST_LAYOUT;

        public MainScreenViewHolder(View mainScreenView) {
            CHILD_LIST_LAYOUT = (LinearLayout) mainScreenView.findViewById(R.id.child_list_layout_view);
            INSTRUCTOR_LIST_LAYOUT = (LinearLayout) mainScreenView.findViewById(R.id.instructor_list_layout_view);
            EMPLOYEE_LIST_LAYOUT = (LinearLayout) mainScreenView.findViewById(R.id.employee_list_layout_view);
            COURSES_LIST_LAYOUT = (LinearLayout) mainScreenView.findViewById(R.id.course_list_layout_view);
            SALARY_LIST_LAYOUT = (LinearLayout) mainScreenView.findViewById(R.id.salary_layout_view);
        }
    }

    public static class ChildInputScreenViewHolder{

        public final EditText CHILD_NAME_EDIT_TEXT;
        public final EditText CHILD_AGE_EDIT_TEXT;
        public final TextView FIRST_ORDER_TEXT_VIEW;
        public final TextView MIDDLE_ORDER_TEXT_VIEW;
        public final TextView LAST_ORDER_TEXT_VIEW;
        public final TextView ALONE_ORDER_TEXT_VIEW;
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
        public final ImageView ARABIC_GOVERNMENTAL_IMAGE_VIEW;
        public final ImageView ENGLISH_GOVERNMENTAL_IMAGE_VIEW;
        public final ImageView ARABIC_PRIVATE_IMAGE_VIEW;
        public final ImageView ENGLISH_PRIVATE_IMAGE_VIEW;
        public final ImageView NONE_EDUCATION_IMAGE_VIEW;
        public final ImageView PRIMARY_EDUCATION_IMAGE_VIEW;
        public final ImageView PREPARATORY_IMAGE_VIEW;
        public final ImageView SECONDARY_EDUCATION_IMAGE_VIEW;
        public final ImageView NONE_YEAR_IMAGE_VIEW;
        public final ImageView FIRST_YEAR_IMAGE_VIEW;
        public final ImageView SECOND_YEAR_IMAGE_VIEW;
        public final ImageView THIRD_YEAR_IMAGE_VIEW;
        public final ImageView FORTH_YEAR_IMAGE_VIEW;
        public final ImageView FIFTH_YEAR_IMAGE_VIEW;
        public final ImageView SIXTH_YEAR_IMAGE_VIEW;
        public final ImageView GOOD_SPEAKER_IMAGE_VIEW;
        public final ImageView SOCIAL_IMAGE_VIEW;
        public final ImageView LEADING_IMAGE_VIEW;
        public final ImageView NEURAL_IMAGE_VIEW;
        public final ImageView COLLABORATOR_IMAGE_VIEW;
        public final ImageView ASKS_FOR_HELP_IMAGE_VIEW;
        public final ImageView WORRIES_IMAGE_VIEW;
        public final ImageView LEAVES_PROBLEMS_IMAGE_VIEW;
        public final ImageView TRIES_SOLVE_IMAGE_VIEW;
        public final ImageView DRAWING_IMAGE_VIEW;
        public final ImageView ELECTRONIC_IMAGE_VIEW;
        public final ImageView WATCHING_TV_IMAGE_VIEW;
        public final ImageView HANDWORK_IMAGE_VIEW;
        public final ImageView FIRST_ORDER_IMAGE_VIEW;
        public final ImageView MIDDLE_ORDER_IMAGE_VIEW;
        public final ImageView LAST_ORDER_IMAGE_VIEW;
        public final ImageView ALONE_ORDER_IMAGE_VIEW;
        public final ImageView MALE_GENDER_IMAGE_VIEW;
        public final ImageView FEMALE_GENDER_IMAGE_VIEW;

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
            ALONE_ORDER_TEXT_VIEW = (TextView) childInputView.findViewById(R.id.child_input_alone_order_text_view);
            SUBMIT_CHILD_BUTTON = (Button) childInputView.findViewById(R.id.child_input_submit_application_button);
            ARABIC_GOVERNMENTAL_IMAGE_VIEW = (ImageView) childInputView.findViewById(R.id.child_input_government_arabic_image_view);
            ENGLISH_GOVERNMENTAL_IMAGE_VIEW = (ImageView) childInputView.findViewById(R.id.child_input_government_english_image_view);
            ARABIC_PRIVATE_IMAGE_VIEW = (ImageView) childInputView.findViewById(R.id.child_input_private_arabic_image_view);
            ENGLISH_PRIVATE_IMAGE_VIEW = (ImageView) childInputView.findViewById(R.id.child_input_private_english_image_view);
            NONE_EDUCATION_IMAGE_VIEW = (ImageView) childInputView.findViewById(R.id.child_input_none_academic_image_view);
            PRIMARY_EDUCATION_IMAGE_VIEW = (ImageView) childInputView.findViewById(R.id.child_input_primary_academic_image_view);
            PREPARATORY_IMAGE_VIEW = (ImageView) childInputView.findViewById(R.id.child_input_preparatory_academic_image_view);
            SECONDARY_EDUCATION_IMAGE_VIEW = (ImageView) childInputView.findViewById(R.id.child_input_secondary_academic_image_view);
            NONE_YEAR_IMAGE_VIEW = (ImageView) childInputView.findViewById(R.id.child_input_none_year_image_view);
            FIRST_YEAR_IMAGE_VIEW = (ImageView) childInputView.findViewById(R.id.child_input_first_year_image_view);
            SECOND_YEAR_IMAGE_VIEW = (ImageView) childInputView.findViewById(R.id.child_input_second_year_image_view);
            THIRD_YEAR_IMAGE_VIEW = (ImageView) childInputView.findViewById(R.id.child_input_third_year_image_view);
            FORTH_YEAR_IMAGE_VIEW = (ImageView) childInputView.findViewById(R.id.child_input_forth_year_image_view);
            FIFTH_YEAR_IMAGE_VIEW = (ImageView) childInputView.findViewById(R.id.child_input_fifth_year_image_view);
            SIXTH_YEAR_IMAGE_VIEW = (ImageView) childInputView.findViewById(R.id.child_input_sixth_year_image_view);
            GOOD_SPEAKER_IMAGE_VIEW = (ImageView) childInputView.findViewById(R.id.child_input_good_speaker_image_view);
            SOCIAL_IMAGE_VIEW = (ImageView) childInputView.findViewById(R.id.child_input_social_image_view);
            LEADING_IMAGE_VIEW = (ImageView) childInputView.findViewById(R.id.child_input_leading_image_view);
            NEURAL_IMAGE_VIEW = (ImageView) childInputView.findViewById(R.id.child_input_neural_image_view);
            COLLABORATOR_IMAGE_VIEW = (ImageView) childInputView.findViewById(R.id.child_input_collaborator_image_view);
            ASKS_FOR_HELP_IMAGE_VIEW = (ImageView) childInputView.findViewById(R.id.child_input_ans1_problem_image_view);
            WORRIES_IMAGE_VIEW = (ImageView) childInputView.findViewById(R.id.child_input_ans2_problem_image_view);
            LEAVES_PROBLEMS_IMAGE_VIEW = (ImageView) childInputView.findViewById(R.id.child_input_ans3_problem_image_view);
            TRIES_SOLVE_IMAGE_VIEW = (ImageView) childInputView.findViewById(R.id.child_input_ans4_problem_image_view);
            DRAWING_IMAGE_VIEW = (ImageView) childInputView.findViewById(R.id.child_input_ans1_time_image_view);
            ELECTRONIC_IMAGE_VIEW = (ImageView) childInputView.findViewById(R.id.child_input_ans2_time_image_view);
            WATCHING_TV_IMAGE_VIEW = (ImageView) childInputView.findViewById(R.id.child_input_ans3_time_image_view);
            HANDWORK_IMAGE_VIEW = (ImageView) childInputView.findViewById(R.id.child_input_ans4_time_image_view);
            FIRST_ORDER_IMAGE_VIEW = (ImageView) childInputView.findViewById(R.id.child_input_first_order_image_view);
            MIDDLE_ORDER_IMAGE_VIEW = (ImageView) childInputView.findViewById(R.id.child_input_middle_order_image_view);
            LAST_ORDER_IMAGE_VIEW = (ImageView) childInputView.findViewById(R.id.child_input_last_order_image_view);
            MALE_GENDER_IMAGE_VIEW = (ImageView) childInputView.findViewById(R.id.child_input_male_gender_image_view);
            FEMALE_GENDER_IMAGE_VIEW = (ImageView) childInputView.findViewById(R.id.child_input_female_gender_image_view);
            ALONE_ORDER_IMAGE_VIEW = (ImageView) childInputView.findViewById(R.id.child_input_alone_order_image_view);
        }

    }

    public static class CourseInputScreenViewHolder{

        public final EditText COURSE_NAME_EDIT_TEXT;
        public final EditText COURSE_HOURS_EDIT_TEXT;
        public final EditText COURSE_COST_EDIT_TEXT;
        public final EditText COURSE_LEVEL_EDIT_TEXT;
        public final EditText COURSE_BEGINNING_DATE_EDIT_TEXT;
        public final EditText COURSE_ENDING_DATE_EDIT_TEXT;
        public final EditText COURSE_SALARY_PER_CHILD_EDIT_TEXT;
        public final EditText COURSE_AGE_RANGE_FROM_EDIT_TEXT;
        public final EditText COURSE_AGE_RANGE_TO_EDIT_TEXT;
        public final TextView COURSE_COMPLETE_TEXT_VIEW;
        public final TextView COURSE_INCOMPLETE_TEXT_VIEW;
        public final ImageView COURSE_COMPLETE_IMAGE_VIEW;
        public final ImageView COURSE_INCOMPLETE_IMAGE_VIEW;
        public final Button SUBMIT_COURSE_BUTTON;

        public CourseInputScreenViewHolder(View courseInputScreen){

            COURSE_NAME_EDIT_TEXT = (EditText) courseInputScreen.findViewById(R.id.course_input_name_edit_view);
            COURSE_HOURS_EDIT_TEXT = (EditText) courseInputScreen.findViewById(R.id.course_input_hours_edit_view);
            COURSE_COST_EDIT_TEXT = (EditText) courseInputScreen.findViewById(R.id.course_input_cost_edit_view);
            COURSE_LEVEL_EDIT_TEXT = (EditText) courseInputScreen.findViewById(R.id.course_input_level_edit_view);
            COURSE_BEGINNING_DATE_EDIT_TEXT = (EditText) courseInputScreen.findViewById(R.id.course_input_start_date_edit_view);
            COURSE_ENDING_DATE_EDIT_TEXT = (EditText) courseInputScreen.findViewById(R.id.course_input_end_date_edit_view);
            COURSE_SALARY_PER_CHILD_EDIT_TEXT = (EditText) courseInputScreen.findViewById(R.id.course_input_salary_per_child_edit_view);
            COURSE_AGE_RANGE_FROM_EDIT_TEXT = (EditText) courseInputScreen.findViewById(R.id.course_input_age_range_from_edit_view);
            COURSE_AGE_RANGE_TO_EDIT_TEXT = (EditText) courseInputScreen.findViewById(R.id.course_input_age_range_to_edit_view);
            COURSE_COMPLETE_TEXT_VIEW = (TextView) courseInputScreen.findViewById(R.id.course_input_complete_text_view);
            COURSE_INCOMPLETE_TEXT_VIEW = (TextView) courseInputScreen.findViewById(R.id.course_input_incomplete_text_view);
            COURSE_COMPLETE_IMAGE_VIEW = (ImageView) courseInputScreen.findViewById(R.id.course_input_complete_image_view);
            COURSE_INCOMPLETE_IMAGE_VIEW = (ImageView) courseInputScreen.findViewById(R.id.course_input_incomplete_image_view);
            SUBMIT_COURSE_BUTTON = (Button) courseInputScreen.findViewById(R.id.course_input_submit_application_button);
        }
    }

    public static class EmployeeInputScreenViewHolder{

        public final EditText EMPLOYEE_NAME_EDIT_TEXT;
        public final EditText EMPLOYEE_AGE_EDIT_TEXT;
        public final EditText EMPLOYEE_ADDRESS_EDIT_TEXT;
        public final EditText EMPLOYEE_MOBILE_EDIT_TEXT;
        public final TextView EMPLOYEE_MALE_GENDER_TEXT_VIEW;
        public final TextView EMPLOYEE_FEMALE_GENDER_TEXT_VIEW;
        public final ImageView EMPLOYEE_MALE_GENDER_IMAGE_VIEW;
        public final ImageView EMPLOYEE_FEMALE_GENDER_IMAGE_VIEW;
        public final EditText EMPLOYEE_SALARY_EDIT_TEXT;
        public final EditText EMPLOYEE_QUALIFICATION_EDIT_TEXT;
        public final Button EMPLOYEE_SUBMIT_BUTTON;

        public EmployeeInputScreenViewHolder(View employeeInputScreen){

            EMPLOYEE_NAME_EDIT_TEXT = (EditText) employeeInputScreen.findViewById(R.id.employee_input_name_edit_view);
            EMPLOYEE_AGE_EDIT_TEXT = (EditText) employeeInputScreen.findViewById(R.id.employee_input_age_edit_view);
            EMPLOYEE_ADDRESS_EDIT_TEXT = (EditText) employeeInputScreen.findViewById(R.id.employee_input_address_edit_view);
            EMPLOYEE_MOBILE_EDIT_TEXT = (EditText) employeeInputScreen.findViewById(R.id.employee_input_mobile_edit_view);
            EMPLOYEE_MALE_GENDER_TEXT_VIEW = (TextView) employeeInputScreen.findViewById(R.id.employee_input_male_gender_text_view);
            EMPLOYEE_FEMALE_GENDER_TEXT_VIEW = (TextView) employeeInputScreen.findViewById(R.id.employee_input_female_gender_text_view);
            EMPLOYEE_MALE_GENDER_IMAGE_VIEW = (ImageView) employeeInputScreen.findViewById(R.id.employee_input_male_gender_image_view);
            EMPLOYEE_FEMALE_GENDER_IMAGE_VIEW = (ImageView) employeeInputScreen.findViewById(R.id.employee_input_female_gender_image_view);
            EMPLOYEE_SALARY_EDIT_TEXT = (EditText) employeeInputScreen.findViewById(R.id.employee_input_original_salary_edit_view);
            EMPLOYEE_SUBMIT_BUTTON = (Button) employeeInputScreen.findViewById(R.id.employee_input_submit_application_button);
            EMPLOYEE_QUALIFICATION_EDIT_TEXT = (EditText) employeeInputScreen.findViewById(R.id.employee_input_qualification_edit_view);

        }
    }

    public static class InstructorInputScreenViewHolder{

        public final EditText INSTRUCTOR_NAME_EDIT_TEXT;
        public final EditText INSTRUCTOR_AGE_EDIT_TEXT;
        public final EditText INSTRUCTOR_ADDRESS_EDIT_TEXT;
        public final EditText INSTRUCTOR_MOBILE_EDIT_TEXT;
        public final ImageView INSTRUCTOR_MALE_GENDER_IMAGE_VIEW;
        public final ImageView INSTRUCTOR_FEMALE_GENDER_IMAGE_VIEW;
        public final TextView INSTRUCTOR_MALE_GENDER_TEXT_VIEW;
        public final TextView INSTRUCTOR_FEMALE_GENDER_TEXT_VIEW;
        public final EditText INSTRUCTOR_QUALIFICATION_EDIT_TEXT;
        public final Button  INSTRUCTOR_SUBMIT_BUTTON;

        public InstructorInputScreenViewHolder(View  InstructorInputScreen){

            INSTRUCTOR_NAME_EDIT_TEXT = (EditText)  InstructorInputScreen.findViewById(R.id.instructor_input_name_edit_view);
            INSTRUCTOR_AGE_EDIT_TEXT = (EditText)  InstructorInputScreen.findViewById(R.id.instructor_input_age_edit_view);
            INSTRUCTOR_ADDRESS_EDIT_TEXT = (EditText)  InstructorInputScreen.findViewById(R.id.instructor_input_address_edit_view);
            INSTRUCTOR_MOBILE_EDIT_TEXT = (EditText)  InstructorInputScreen.findViewById(R.id.instructor_input_mobile_edit_view);
            INSTRUCTOR_MALE_GENDER_TEXT_VIEW = (TextView)  InstructorInputScreen.findViewById(R.id.instructor_input_male_gender_text_view);
            INSTRUCTOR_FEMALE_GENDER_TEXT_VIEW = (TextView)  InstructorInputScreen.findViewById(R.id.instructor_input_female_gender_text_view);
            INSTRUCTOR_MALE_GENDER_IMAGE_VIEW = (ImageView)  InstructorInputScreen.findViewById(R.id.instructor_input_male_gender_image_view);
            INSTRUCTOR_FEMALE_GENDER_IMAGE_VIEW = (ImageView)  InstructorInputScreen.findViewById(R.id.instructor_input_female_gender_image_view);
            INSTRUCTOR_QUALIFICATION_EDIT_TEXT = (EditText)  InstructorInputScreen.findViewById(R.id.instructor_input_qualification_edit_view);
            INSTRUCTOR_SUBMIT_BUTTON = (Button)  InstructorInputScreen.findViewById(R.id.instructor_input_submit_application_button);

        }
    }

    public static class ChildListScreenViewHolder{

        public final FloatingActionButton ADD_NEW_CHILD_BUTTON;
        public final ListView CHILD_LIST_VIEW;
        public final LinearLayout CHILD_LIST_EMPTY_VIEW;

        public ChildListScreenViewHolder(View childListView){
            ADD_NEW_CHILD_BUTTON = (FloatingActionButton) childListView.findViewById(R.id.child_add_new_floating_button);
            CHILD_LIST_VIEW = (ListView) childListView.findViewById(R.id.child_list_view);
            CHILD_LIST_EMPTY_VIEW = (LinearLayout) childListView.findViewById(R.id.child_list_empty_view);
        }

        public static class ChildListRecycleViewHolder{

            public final TextView CHILD_NAME_TEXT_VIEW;
            public final TextView CHILD_AGE_TEXT_VIEW;
            public final TextView CHILD_COURSES_TEXT_VIEW;
            public final ImageView CHILD_DELETE_IMAGE_VIEW;

            public ChildListRecycleViewHolder(View childListRecycleView){

                CHILD_NAME_TEXT_VIEW = (TextView) childListRecycleView.findViewById(R.id.child_list_name_text_view);
                CHILD_AGE_TEXT_VIEW = (TextView) childListRecycleView.findViewById(R.id.child_list_age_text_view);
                CHILD_COURSES_TEXT_VIEW = (TextView) childListRecycleView.findViewById(R.id.child_list_courses_text_view);
                CHILD_DELETE_IMAGE_VIEW = (ImageView) childListRecycleView.findViewById(R.id.child_list_delete_child_image_view);
            }
        }
    }

    public static class CourseListScreenViewHolder{

        public final FloatingActionButton ADD_NEW_COURSE_BUTTON;
        public final ListView COURSE_LIST_VIEW;
        public final LinearLayout COURSE_LIST_EMPTY_VIEW;

        public CourseListScreenViewHolder(View courseListView){
            ADD_NEW_COURSE_BUTTON = (FloatingActionButton) courseListView.findViewById(R.id.course_add_new_floating_button);
            COURSE_LIST_VIEW = (ListView) courseListView.findViewById(R.id.course_list_view);
            COURSE_LIST_EMPTY_VIEW = (LinearLayout) courseListView.findViewById(R.id.course_list_empty_view);
        }

        public static class CourseListRecycleViewHolder{

            public final TextView COURSE_NAME_TEXT_VIEW;
            public final TextView COURSE_INSTRUCTOR_TEXT_VIEW;
            public final TextView COURSE_DURATION_TEXT_VIEW;
            public final ImageView COURSE_DELETE_IMAGE_VIEW;

            public CourseListRecycleViewHolder(View courseListRecycleView){

                COURSE_NAME_TEXT_VIEW = (TextView) courseListRecycleView.findViewById(R.id.course_list_name_text_view);
                COURSE_INSTRUCTOR_TEXT_VIEW = (TextView) courseListRecycleView.findViewById(R.id.course_list_instructor_text_view);
                COURSE_DURATION_TEXT_VIEW = (TextView) courseListRecycleView.findViewById(R.id.course_list_duration_text_view);
                COURSE_DELETE_IMAGE_VIEW = (ImageView) courseListRecycleView.findViewById(R.id.course_list_delete_course_image_view);
            }
        }
    }

    public static class EmployeeListScreenViewHolder{

        public final FloatingActionButton ADD_NEW_EMPLOYEE_BUTTON;
        public final ListView EMPLOYEE_LIST_VIEW;
        public final LinearLayout EMPLOYEE_LIST_EMPTY_VIEW;

        public EmployeeListScreenViewHolder(View employeeListView){
            ADD_NEW_EMPLOYEE_BUTTON = (FloatingActionButton) employeeListView.findViewById(R.id.employee_add_new_floating_button);
            EMPLOYEE_LIST_VIEW = (ListView) employeeListView.findViewById(R.id.employee_list_view);
            EMPLOYEE_LIST_EMPTY_VIEW = (LinearLayout) employeeListView.findViewById(R.id.employee_list_empty_view);
        }

        public static class EmployeeListRecycleViewHolder{

            public final TextView EMPLOYEE_NAME_TEXT_VIEW;
            public final TextView EMPLOYEE_MOBILE_TEXT_VIEW;
            public final ImageView EMPLOYEE_DELETE_IMAGE_VIEW;

            public EmployeeListRecycleViewHolder(View employeeListRecycleView){

                EMPLOYEE_NAME_TEXT_VIEW = (TextView) employeeListRecycleView.findViewById(R.id.employee_list_name_text_view);
                EMPLOYEE_MOBILE_TEXT_VIEW = (TextView) employeeListRecycleView.findViewById(R.id.employee_List_mobile_text_view);
                EMPLOYEE_DELETE_IMAGE_VIEW = (ImageView) employeeListRecycleView.findViewById(R.id.employee_list_delete_employee_image_view);
            }
        }
    }

    public static class InstructorListScreenViewHolder{

        public final FloatingActionButton ADD_NEW_INSTRUCTOR_BUTTON;
        public final ListView INSTRUCTOR_LIST_VIEW;
        public final LinearLayout INSTRUCT0R_LIST_EMPTY_VIEW;

        public InstructorListScreenViewHolder(View instructorListView){
            ADD_NEW_INSTRUCTOR_BUTTON = (FloatingActionButton) instructorListView.findViewById(R.id.instructor_add_new_floating_button);
            INSTRUCTOR_LIST_VIEW = (ListView) instructorListView.findViewById(R.id.instructor_list_view);
            INSTRUCT0R_LIST_EMPTY_VIEW = (LinearLayout) instructorListView.findViewById(R.id.instructor_list_empty_view);
        }

        public static class InstructorListRecycleViewHolder{

            public final TextView INSTRUCTOR_NAME_TEXT_VIEW;
            public final TextView INSTRUCTOR_COURSES_TEXT_VIEW;
            public final ImageView INSTRUCTOR_DELETE_IMAGE_VIEW;

            public InstructorListRecycleViewHolder(View instructorListRecycleView){
                INSTRUCTOR_NAME_TEXT_VIEW = (TextView) instructorListRecycleView.findViewById(R.id.instructor_list_name_text_view);
                INSTRUCTOR_COURSES_TEXT_VIEW = (TextView) instructorListRecycleView.findViewById(R.id.instructor_list_courses_text_view);
                INSTRUCTOR_DELETE_IMAGE_VIEW = (ImageView) instructorListRecycleView.findViewById(R.id.instructor_list_delete_instructor_image_view);
            }
        }
    }

    public static class ChildProfileScreenViewHolder{

        public final TextView CHILD_NAME_TEXT_VIEW;
        public final TextView CHILD_AGE_TEXT_VIEW;
        public final TextView CHILD_GENDER_TEXT_VIEW;
        public final TextView CHILD_BIRTH_ORDER_TEXT_VIEW;
        public final TextView MOTHER_NAME_TEXT_VIEW;
        public final TextView MOTHER_MOBILE_TEXT_VIEW;
        public final TextView MOTHER_QUALIFICATION_TEXT_VIEW;
        public final TextView MOTHER_JOB_TEXT_VIEW;
        public final TextView FATHER_NAME_TEXT_VIEW;
        public final TextView FATHER_MOBILE_TEXT_VIEW;
        public final TextView FATHER_JOB_TEXT_VIEW;
        public final TextView WHATSAPP_TEXT_VIEW;
        public final TextView EDUCATION_TYPE_TEXT_VIEW;
        public final TextView EDUCATION_STAGE_TEXT_VIEW;
        public final TextView CHILD_CHARACTERISTIC_TEXT_VIEW;
        public final TextView CHILD_HANDLING_PROBLEM_TEXT_VIEW;
        public final TextView CHILD_FREE_TIME_TEXT_VIEW;
        public final FloatingActionButton CHILD_EDIT_BUTTON;
        public final RecyclerView CHILD_COURSES_RECYCLE_VIEW;
        public final LinearLayout EMPTY_VIEW_LAYOUT;

        public ChildProfileScreenViewHolder(View childProfileView){

            CHILD_NAME_TEXT_VIEW = (TextView) childProfileView.findViewById(R.id.child_pf_name_text_view);
            CHILD_AGE_TEXT_VIEW = (TextView) childProfileView.findViewById(R.id.child_pf_age_text_view);
            CHILD_GENDER_TEXT_VIEW = (TextView) childProfileView.findViewById(R.id.child_pf_gender_text_view);
            CHILD_BIRTH_ORDER_TEXT_VIEW = (TextView) childProfileView.findViewById(R.id.child_pf_birth_order_text_view);
            MOTHER_NAME_TEXT_VIEW = (TextView) childProfileView.findViewById(R.id.child_pf_mother_name_text_view);
            MOTHER_MOBILE_TEXT_VIEW = (TextView) childProfileView.findViewById(R.id.child_pf_mother_mobile_text_view);
            MOTHER_QUALIFICATION_TEXT_VIEW = (TextView) childProfileView.findViewById(R.id.child_pf_mother_qualification_text_view);
            MOTHER_JOB_TEXT_VIEW = (TextView) childProfileView.findViewById(R.id.child_pf_mother_job_text_view);
            FATHER_NAME_TEXT_VIEW = (TextView) childProfileView.findViewById(R.id.child_pf_father_name_text_view);
            FATHER_MOBILE_TEXT_VIEW = (TextView) childProfileView.findViewById(R.id.child_pf_father_mobile_text_view);
            FATHER_JOB_TEXT_VIEW = (TextView) childProfileView.findViewById(R.id.child_pf_father_job_text_view);
            WHATSAPP_TEXT_VIEW = (TextView) childProfileView.findViewById(R.id.child_pf_whatsapp_text_view);
            EDUCATION_TYPE_TEXT_VIEW = (TextView) childProfileView.findViewById(R.id.child_pf_education_type_text_view);
            EDUCATION_STAGE_TEXT_VIEW = (TextView) childProfileView.findViewById(R.id.child_pf_education_stage__text_view);
            CHILD_CHARACTERISTIC_TEXT_VIEW = (TextView) childProfileView.findViewById(R.id.child_pf_characteristic_text_view);
            CHILD_HANDLING_PROBLEM_TEXT_VIEW = (TextView) childProfileView.findViewById(R.id.child_pf_problem_handling_text_view);
            CHILD_FREE_TIME_TEXT_VIEW = (TextView) childProfileView.findViewById(R.id.child_pf_free_time_text_view);
            CHILD_EDIT_BUTTON = (FloatingActionButton) childProfileView.findViewById(R.id.child_pf_edit_floating_button);
            CHILD_COURSES_RECYCLE_VIEW = (RecyclerView) childProfileView.findViewById(R.id.child_pf_courses_instructors_grid_view);
            EMPTY_VIEW_LAYOUT = (LinearLayout) childProfileView.findViewById(R.id.child_pf_empty_view_layout);
        }

        public static class ChildProfileListViewHolder extends RecyclerView.ViewHolder{

            public final TextView INSTRUCTOR_NAME_TEXT_VIEW;
            public final TextView COURSE_NAME_TEXT_VIEW;
            public final TextView START_DATE_TEXT_VIEW;
            public final TextView END_DATE_TEXT_VIEW;

            public ChildProfileListViewHolder(View childProfileListView){
                super(childProfileListView);
                INSTRUCTOR_NAME_TEXT_VIEW = (TextView) childProfileListView.findViewById(R.id.child_pf_instructor_name_text_view);
                COURSE_NAME_TEXT_VIEW = (TextView) childProfileListView.findViewById(R.id.child_pf_course_name_text_view);
                START_DATE_TEXT_VIEW = (TextView) childProfileListView.findViewById(R.id.child_pf_course_start_date_text_view);
                END_DATE_TEXT_VIEW = (TextView) childProfileListView.findViewById(R.id.child_pf_course_end_date_text_view);

            }
        }
    }

    public static class CourseProfileScreenViewHolder{

        public final TextView COURSE_NAME_TEXT_VIEW;
        public final TextView COURSE_HOURS_TEXT_VIEW;
        public final TextView COURSE_LEVEL_TEXT_VIEW;
        public final TextView COURSE_COST_TEXT_VIEW;
        public final TextView COURSE_START_DATE_TEXT_VIEW;
        public final TextView COURSE_END_DATE_TEXT_VIEW;
        public final TextView COURSE_SALARY_PER_CHILD_TEXT_VIEW;
        public final TextView COURSE_START_TEXT_VIEW;
        public final TextView COURSE_INSTRUCTOR_NAME_TEXT_VIEW;
        public final TextView COURSE_AGE_RANGE_TEXT_VIEW;
        public final FloatingActionButton COURSE_EDIT_BUTTON;

        public CourseProfileScreenViewHolder(View courseProfileView){

            COURSE_NAME_TEXT_VIEW = (TextView) courseProfileView.findViewById(R.id.course_pf_name_text_view);
            COURSE_HOURS_TEXT_VIEW = (TextView) courseProfileView.findViewById(R.id.course_pf_hours_text_view);
            COURSE_LEVEL_TEXT_VIEW = (TextView) courseProfileView.findViewById(R.id.course_pf_level_text_view);
            COURSE_COST_TEXT_VIEW = (TextView) courseProfileView.findViewById(R.id.course_pf_cost_text_view);
            COURSE_START_DATE_TEXT_VIEW = (TextView) courseProfileView.findViewById(R.id.course_pf_start_date_text_view);
            COURSE_END_DATE_TEXT_VIEW = (TextView) courseProfileView.findViewById(R.id.course_pf_end_date_text_view);
            COURSE_SALARY_PER_CHILD_TEXT_VIEW = (TextView) courseProfileView.findViewById(R.id.course_pf_salary_per_child_text_view);
            COURSE_START_TEXT_VIEW = (TextView) courseProfileView.findViewById(R.id.course_pf_state_text_view);
            COURSE_INSTRUCTOR_NAME_TEXT_VIEW = (TextView) courseProfileView.findViewById(R.id.course_pf_instructor_name_text_view);
            COURSE_AGE_RANGE_TEXT_VIEW = (TextView) courseProfileView.findViewById(R.id.course_pf_age_range_text_view);
            COURSE_EDIT_BUTTON = (FloatingActionButton) courseProfileView.findViewById(R.id.course_pf_edit_floating_button);
        }
    }

    public static class EmployeeProfileScreenViewHolder{

        public final TextView EMPLOYEE_NAME_TEXT_VIEW;
        public final TextView EMPLOYEE_AGE_TEXT_VIEW;
        public final TextView EMPLOYEE_GENDER_TEXT_VIEW;
        public final TextView EMPLOYEE_MOBILE_TEXT_VIEW;
        public final TextView EMPLOYEE_ADDRESS_TEXT_VIEW;
        public final TextView EMPLOYEE_QUALIFICATION_TEXT_VIEW;
        public final TextView EMPLOYEE_SALARY_TEXT_VIEW;
        public final FloatingActionButton EMPLOYEE_EDIT_BUTTON;

        public EmployeeProfileScreenViewHolder(View employeeProfileView){

            EMPLOYEE_NAME_TEXT_VIEW = (TextView) employeeProfileView.findViewById(R.id.employee_pf_name_text_view);
            EMPLOYEE_AGE_TEXT_VIEW = (TextView) employeeProfileView.findViewById(R.id.employee_pf_age_text_view);
            EMPLOYEE_GENDER_TEXT_VIEW = (TextView) employeeProfileView.findViewById(R.id.employee_pf_gender_text_view);
            EMPLOYEE_MOBILE_TEXT_VIEW = (TextView) employeeProfileView.findViewById(R.id.employee_pf_mobile_text_view);
            EMPLOYEE_ADDRESS_TEXT_VIEW = (TextView) employeeProfileView.findViewById(R.id.employee_pf_address_text_view);
            EMPLOYEE_QUALIFICATION_TEXT_VIEW = (TextView) employeeProfileView.findViewById(R.id.employee_pf_qualification_text_view);
            EMPLOYEE_SALARY_TEXT_VIEW = (TextView) employeeProfileView.findViewById(R.id.employee_pf_original_salary_text_view);
            EMPLOYEE_EDIT_BUTTON = (FloatingActionButton) employeeProfileView.findViewById(R.id.employee_pf_edit_floating_button);
        }
    }

    public static class InstructorProfileScreenViewHolder{

        public final TextView INSTRUCTOR_NAME_TEXT_VIEW;
        public final TextView INSTRUCTOR_AGE_TEXT_VIEW;
        public final TextView INSTRUCTOR_GENDER_TEXT_VIEW;
        public final TextView INSTRUCTOR_MOBILE_TEXT_VIEW;
        public final TextView INSTRUCTOR_ADDRESS_TEXT_VIEW;
        public final TextView INSTRUCTOR_QUALIFICATION_TEXT_VIEW;
        public final TextView INSTRUCTOR_CV_TEXT_VIEW;
        public final RecyclerView INSTRUCTOR_COURSES_CHILD_RECYCLE_VIEW;
        public final FloatingActionButton INSTRUCTOR_EDIT_BUTTON;
        public final LinearLayout INSTRUCTOR_COURSES_LIST_EMPTY_VIEW;

        public InstructorProfileScreenViewHolder(View instructorProfileView){

            INSTRUCTOR_NAME_TEXT_VIEW = (TextView) instructorProfileView.findViewById(R.id.instructor_pf_name_text_view);
            INSTRUCTOR_AGE_TEXT_VIEW = (TextView) instructorProfileView.findViewById(R.id.instructor_pf_age_text_view);
            INSTRUCTOR_GENDER_TEXT_VIEW = (TextView) instructorProfileView.findViewById(R.id.instructor_pf_gender_text_view);
            INSTRUCTOR_MOBILE_TEXT_VIEW = (TextView) instructorProfileView.findViewById(R.id.instructor_pf_mobile_text_view);
            INSTRUCTOR_ADDRESS_TEXT_VIEW = (TextView) instructorProfileView.findViewById(R.id.instructor_pf_address_text_view);
            INSTRUCTOR_QUALIFICATION_TEXT_VIEW = (TextView) instructorProfileView.findViewById(R.id.instructor_pf_qualification_text_view);
            INSTRUCTOR_CV_TEXT_VIEW = (TextView) instructorProfileView.findViewById(R.id.instructor_pf_cv_text_view);
            INSTRUCTOR_COURSES_CHILD_RECYCLE_VIEW = (RecyclerView) instructorProfileView.findViewById(R.id.instructor_pf_courses_children_recycle_view);
            INSTRUCTOR_EDIT_BUTTON = (FloatingActionButton) instructorProfileView.findViewById(R.id.instructor_pf_edit_floating_button);
            INSTRUCTOR_COURSES_LIST_EMPTY_VIEW = (LinearLayout) instructorProfileView.findViewById(R.id.instructor_pf_empty_view_layout);
        }

        public static class InstructorCoursesViewHolder extends RecyclerView.ViewHolder{

            public final TextView COURSE_NAME_TEXT_VIEW;
            public final TextView COURSE_CHILDREN_TEXT_VIEW;
            public final TextView COURSE_START_DATE_TEXT_VIEW;
            public final TextView COURSE_END_DATE_TEXT_VIEW;

            public InstructorCoursesViewHolder(View instructorCoursesView){
                super(instructorCoursesView);
                COURSE_NAME_TEXT_VIEW = (TextView) instructorCoursesView.findViewById(R.id.instructor_pf_course_name_text_view);
                COURSE_CHILDREN_TEXT_VIEW = (TextView) instructorCoursesView.findViewById(R.id.instructor_pf_children_text_view);
                COURSE_START_DATE_TEXT_VIEW = (TextView) instructorCoursesView.findViewById(R.id.instructor_pf_course_start_date_text_view);
                COURSE_END_DATE_TEXT_VIEW = (TextView) instructorCoursesView.findViewById(R.id.instructor_pf_course_end_date_text_view);
            }
        }
    }

    public static class ChildCourseConnectorScreenViewHolder{

        public final Button SUBMIT_BUTTON;
        public final Button REST_BUTTON;
        public final ListView COURSES_LIST_VIEW;
        public final LinearLayout EMPTY_LIST_LAYOUT;

        public ChildCourseConnectorScreenViewHolder(View childCourseView){

            SUBMIT_BUTTON = (Button) childCourseView.findViewById(R.id.child_course_submit_button);
            REST_BUTTON = (Button) childCourseView.findViewById(R.id.child_course_rest_button);
            COURSES_LIST_VIEW = (ListView) childCourseView.findViewById(R.id.child_course_courses_list_view);
            EMPTY_LIST_LAYOUT = (LinearLayout) childCourseView.findViewById(R.id.child_pf_connector_empty_view_layout);
        }

        public static class CoursesViewHolder{

            public final TextView COURSE_NAME_TEXT_VIEW;
            public final TextView COURSE_COST_TEXT_VIEW;
            public final TextView COURSE_HOURS_TEXT_VIEW;
            public final TextView COURSE_START_DATE_TEXT_VIEW;
            public final TextView COURSE_END_DATE_TEXT_VIEW;
            public final ImageView COURSE_SELECT_IMAGE_VIEW;

            public CoursesViewHolder(View coursesView){

                COURSE_NAME_TEXT_VIEW = (TextView) coursesView.findViewById(R.id.child_course_list_name_text_view);
                COURSE_COST_TEXT_VIEW = (TextView) coursesView.findViewById(R.id.child_course_list_cost_text_view);
                COURSE_HOURS_TEXT_VIEW = (TextView) coursesView.findViewById(R.id.child_course_list_hours_text_view);
                COURSE_START_DATE_TEXT_VIEW = (TextView) coursesView.findViewById(R.id.child_course_list_start_date_text_view);
                COURSE_END_DATE_TEXT_VIEW = (TextView) coursesView.findViewById(R.id.child_course_list_end_date_text_view);
                COURSE_SELECT_IMAGE_VIEW = (ImageView) coursesView.findViewById(R.id.child_course_list_select__image_view);
            }
        }
    }

    public static class InstructorCourseConnectorScreenViewHolder{

        public final Button SUBMIT_BUTTON;
        public final Button REST_BUTTON;
        public final ListView COURSES_LIST_VIEW;
        public final LinearLayout INSTRUCTOR_COURSE_LIST_EMPTY_VIEW;

        public InstructorCourseConnectorScreenViewHolder(View instructorCourseView){

            SUBMIT_BUTTON = (Button) instructorCourseView.findViewById(R.id.instructor_course_submit_button);
            REST_BUTTON = (Button) instructorCourseView.findViewById(R.id.instructor_course_rest_button);
            COURSES_LIST_VIEW = (ListView) instructorCourseView.findViewById(R.id.instructor_course_list_view);
            INSTRUCTOR_COURSE_LIST_EMPTY_VIEW = (LinearLayout) instructorCourseView.findViewById(R.id.instructor_course_connector_empty_view_layout);
        }

        public static class CoursesViewHolder{

            public final TextView COURSE_NAME_TEXT_VIEW;
            public final TextView COURSE_SALARY_PER_CHILD_TEXT_VIEW;
            public final TextView COURSE_HOURS_TEXT_VIEW;
            public final TextView COURSE_START_DATE_TEXT_VIEW;
            public final TextView COURSE_END_DATE_TEXT_VIEW;
            public final ImageView COURSE_SELECT_IMAGE_VIEW;

            public CoursesViewHolder(View coursesView){

                COURSE_NAME_TEXT_VIEW = (TextView) coursesView.findViewById(R.id.instructor_course_list_name_text_view);
                COURSE_SALARY_PER_CHILD_TEXT_VIEW = (TextView) coursesView.findViewById(R.id.instructor_course_list_salary_per_child_text_view);
                COURSE_HOURS_TEXT_VIEW = (TextView) coursesView.findViewById(R.id.instructor_course_list_hours_text_view);
                COURSE_START_DATE_TEXT_VIEW = (TextView) coursesView.findViewById(R.id.instructor_course_list_start_date_text_view);
                COURSE_END_DATE_TEXT_VIEW = (TextView) coursesView.findViewById(R.id.instructor_course_list_end_date_text_view);
                COURSE_SELECT_IMAGE_VIEW = (ImageView) coursesView.findViewById(R.id.instructor_course_list_select__image_view);
            }
        }
    }

    public static class InstructorSalaryScreenViewHolder{

        public final TextView COMPLETED_COURSES_TEXT_VIEW;
        public final TextView UNDER_PROGRESS_COURSES_TEXT_VIEW;
        public final TextView TOTAL_PAID_SALARY_TEXT_VIEW;
        public final TextView TOTAL_UNPAID_SALARY_TEXT_VIEW;
        public final TextView NUMBER_OF_PAID_COURSES;
        public final TextView NUMBER_OF_UNPAID_COURSES;
        public final ListView INSTRUCTOR_COURSES_LIST_VIEW;

        public InstructorSalaryScreenViewHolder(View instructorSalaryView){

            COMPLETED_COURSES_TEXT_VIEW = (TextView) instructorSalaryView.findViewById(R.id.instructor_salary_completed_courses_text_view);
            UNDER_PROGRESS_COURSES_TEXT_VIEW = (TextView) instructorSalaryView.findViewById(R.id.instructor_salary_under_progress_text_view);
            TOTAL_PAID_SALARY_TEXT_VIEW = (TextView) instructorSalaryView.findViewById(R.id.instructor_salary_total_paid_salary_text_view);
            TOTAL_UNPAID_SALARY_TEXT_VIEW = (TextView) instructorSalaryView.findViewById(R.id.instructor_salary_total_unpaid_salary_text_view);
            NUMBER_OF_PAID_COURSES = (TextView) instructorSalaryView.findViewById(R.id.instructor_salary_number_of_paid_courses_text_view);
            NUMBER_OF_UNPAID_COURSES = (TextView) instructorSalaryView.findViewById(R.id.instructor_salary_number_of_unpaid_courses_text_view);
            INSTRUCTOR_COURSES_LIST_VIEW = (ListView) instructorSalaryView.findViewById(R.id.instructor_salary_instructors_list_view);
        }

        public static class InstructorCoursesViewHolder{

            public final TextView COURSE_NAME_TEXT_VIEW;
            public final TextView COURSE_START_DATE_TEXT_VIEW;
            public final TextView COURSE_END_DATE_TEXT_VIEW;
            public final TextView COURSE_SALARY_TEXT_VIEW;
            public final Button COURSE_SALARY_STATE_BUTTON;

            public InstructorCoursesViewHolder(View instructorCoursesView){

                COURSE_NAME_TEXT_VIEW = (TextView) instructorCoursesView.findViewById(R.id.instructor_salary_course_name_text_view);
                COURSE_START_DATE_TEXT_VIEW = (TextView) instructorCoursesView.findViewById(R.id.instructor_salary_start_date_text_view);
                COURSE_END_DATE_TEXT_VIEW = (TextView) instructorCoursesView.findViewById(R.id.instructor_salary_end_date_text_view);
                COURSE_SALARY_TEXT_VIEW = (TextView) instructorCoursesView.findViewById(R.id.instructor_salary_course_salary_text_view);
                COURSE_SALARY_STATE_BUTTON = (Button) instructorCoursesView.findViewById(R.id.instructor_salary_instructor_salary_state_button);
            }
        }
    }

    public static class SalaryScreenViewHolder{

        public final TextView TOTAL_UNPAID_SALARY_TEXT_VIEW;
        public final TextView TOTAL_PAID_SALARY_TEXT_VIEW;
        public final TextView UNPAID_SALARY_NUMBER_TEXT_VIEW;
        public final ListView INSTRUCTORS_LIST_VIEW;

        public SalaryScreenViewHolder(View salaryView){

            TOTAL_UNPAID_SALARY_TEXT_VIEW = (TextView) salaryView.findViewById(R.id.salary_total_unpaid_text_view);
            TOTAL_PAID_SALARY_TEXT_VIEW = (TextView) salaryView.findViewById(R.id.salary_total_paid_text_view);
            UNPAID_SALARY_NUMBER_TEXT_VIEW = (TextView) salaryView.findViewById(R.id.salary_number_of_unpaid_text_view);
            INSTRUCTORS_LIST_VIEW = (ListView) salaryView.findViewById(R.id.salary_instructors_list_view);
        }

        public static class InstructorsViewHolder{

            public final TextView INSTRUCTOR_NAME_TEXT_VIEW;
            public final TextView INSTRUCTOR_COURSE_TEXT_VIEW;
            public final TextView INSTRUCTOR_SALARY_PROGRESS_STATE_TEXT_VIEW;

            public InstructorsViewHolder(View instructorsView){

                INSTRUCTOR_NAME_TEXT_VIEW = (TextView) instructorsView.findViewById(R.id.salary_list_instructor_name_text_view);
                INSTRUCTOR_COURSE_TEXT_VIEW = (TextView) instructorsView.findViewById(R.id.salary_list_instructor_course_text_view);
                INSTRUCTOR_SALARY_PROGRESS_STATE_TEXT_VIEW = (TextView) instructorsView.findViewById(R.id.salary_list_salary_process_text_view);
            }
        }
    }

}
