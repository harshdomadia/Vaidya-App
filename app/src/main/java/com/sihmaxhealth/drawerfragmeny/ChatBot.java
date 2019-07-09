package com.sihmaxhealth.drawerfragmeny;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatMultiAutoCompleteTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import javax.xml.transform.dom.DOMLocator;

public class ChatBot extends Fragment {
    private ListView mListView;
    private FloatingActionButton mButtonSend;
    private AppCompatMultiAutoCompleteTextView mEditTextMessage;
    private ImageView mImageView;
    public int prevSym=-1;
    public static int hindi=0;
    public int english=0;
    private ChatMessageAdapter mAdapter;
    public Fuzzy_Predictor diagnostic;
    public Button yesButt;
    public Button noButt;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true); //Will ignore onDestroy Method (Nested Fragments no need this if parent have it)
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //setRetainInstance(true);
        return inflater.inflate(R.layout.chatbot_layout,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Main Logic Code
        HashMap<String,HashMap<String,Integer>> database= new HashMap<>();
        final HashMap<String,Integer> revMapForsymps = new HashMap<>();
        HashMap<String,Integer> revMapForDiseases = new HashMap<>();

        //final String symps[] ={"itching", "skin_rash", "nodal_skin_eruptions", "continuous_sneezing", "shivering", "chills", "joint_pain", "stomach_pain", "acidity", "ulcers_on_tongue", "muscle_wasting", "vomiting", "burning_micturition", "spotting_ urination", "fatigue", "Unnamed: 15", "anxiety", "cold_hands_and_feets", "mood_swings", "weight_loss", "restlessness", "lethargy", "patches_in_throat", "irregular_sugar_level", "cough", "high_fever", "sunken_eyes", "breathlessness", "sweating", "dehydration", "indigestion", "headache", "yellowish_skin", "dark_urine", "nausea", "loss_of_appetite", "pain_behind_the_eyes", "back_pain", "constipation", "abdominal_pain", "diarrhoea", "mild_fever", "yellow_urine", "yellowing_of_eyes", "swelling_of_stomach", "swelled_lymph_nodes", "malaise", "blurred_and_distorted_vision", "phlegm", "throat_irritation", "redness_of_eyes", "sinus_pressure", "runny_nose", "congestion", "chest_pain", "weakness_in_limbs", "fast_heart_rate", "pain_during_bowel_movements", "pain_in_anal_region", "bloody_stool", "irritation_in_anus", "neck_pain", "dizziness", "cramps", "bruising", "obesity", "swollen_legs", "swollen_blood_vessels", "puffy_face_and_eyes", "enlarged_thyroid", "brittle_nails", "swollen_extremeties", "excessive_hunger", "extra_marital_contacts", "drying_and_tingling_lips", "slurred_speech", "knee_pain", "hip_joint_pain", "muscle_weakness", "stiff_neck", "swelling_joints", "movement_stiffness", "spinning_movements", "loss_of_balance", "unsteadiness", "weakness_of_one_body_side", "loss_of_smell", "bladder_discomfort", "foul_smell_of urine", "continuous_feel_of_urine", "passage_of_gases", "depression", "irritability", "muscle_pain", "altered_sensorium", "red_spots_over_body", "belly_pain", "abnormal_menstruation", "dischromic _patches", "watering_from_eyes", "increased_appetite", "family_history", "mucoid_sputum", "rusty_sputum", "lack_of_concentration", "visual_disturbances", "receiving_blood_transfusion", "receiving_unsterile_injections", "coma", "distention_of_abdomen", "history_of_alcohol_consumption", "fluid_overload.1", "blood_in_sputum", "prominent_veins_on_calf", "palpitations", "painful_walking", "pus_filled_pimples", "blackheads", "scurring", "skin_peeling", "silver_like_dusting", "small_dents_in_nails", "inflammatory_nails", "blister", "red_sore_around_nose", "yellow_crust_ooze"};
        final String diseases[]={"(vertigo) Paroymsal  Positional Vertigo", "Alcoholic hepatitis", "Allergy", "Cervical spondylosis", "Chicken pox", "Common Cold", "Dengue", "Dimorphic hemmorhoids(piles)", "Drug Reaction", "Fungal infection", "GERD", "Gastroenteritis", "Heart attack", "Hepatitis B", "Hepatitis C", "Hepatitis D", "Hepatitis E", "Hypertension", "Hyperthyroidism", "Hypoglycemia", "Hypothyroidism", "Impetigo", "Jaundice", "Malaria", "Migraine", "Osteoarthristis", "Paralysis (brain hemorrhage)", "Peptic ulcer disease", "Pneumonia", "Psoriasis","Tuberculosis", "Typhoid", "Urinary tract infection", "Varicose veins", "hepatitis A"};        HashMap<String,String> multi=new HashMap<String,String>();
        final String symps[]={
                "itching",
                "skin_rash",
                "nodal_skin_eruptions",
                "continuous_sneezing",
                "shivering",
                "joint_pain",
                "stomach_pain",
                "acidity",
                "ulcers_on_tongue",
                "vomiting",
                "burning_micturition",
                "spotting_urination",
                "fatigue",
                "anxiety",
                "cold_hands_and_feets",
                "mood_swings",
                "weight_loss",
                "restlessness",
                "lethargy",
                "cough",
                "high_fever",
                "sunken_eyes",
                "breathlessness",
                "sweating",
                "dehydration",
                "indigestion",
                "headache",
                "yellowish_skin",
                "dark_urine",
                "nausea",
                "loss_of_appetite",
                "pain_behind_the_eyes",
                "back_pain",
                "constipation",
                "abdominal_pain",
                "diarrhoea",
                "mild_fever",
                "yellow_urine",
                "yellowing_of_eyes",
                "swelling_of_stomach",
                "swelled_lymph_nodes",
                "malaise",
                "blurred_and_distorted_vision",
                "phlegm",
                "throat_irritation",
                "redness_of_eyes",
                "sinus_pressure",
                "runny_nose",
                "congestion",
                "chest_pain",
                "weakness_in_limbs",
                "fast_heart_rate",
                "pain_during_bowel_movements",
                "pain_in_anal_region",
                "bloody_stool",
                "irritation_in_anus",
                "neck_pain",
                "dizziness",
                "cramps",
                "bruising",
                "obesity",
                "swollen_legs",
                "swollen_blood_vessels",
                "puffy_face_and_eyes",
                "enlarged_thyroid",
                "brittle_nails",
                "swollen_extremeties",
                "excessive_hunger",
                "drying_and_tingling_lips",
                "slurred_speech",
                "knee_pain",
                "hip_joint_pain",
                "muscle_weakness",
                "stiff_neck",
                "swelling_joints",
                "spinning_movements",
                "loss_of_balance",
                "weakness_of_one_body_side",
                "loss_of_smell",
                "bladder_discomfort",
                "foul_smell_of_urine",
                "continuous_feel_of_urine",
                "passage_of_gases",
                "depression",
                "irritability",
                "muscle_pain",
                "altered_sensorium",
                "red_spots_over_body",
                "belly_pain",
                "abnormal_menstruation",
                "dischromic__patches",
                "watering_from_eyes",
                "family_history",
                "rusty_sputum",
                "lack_of_concentration",
                "visual_disturbances",
                "receiving_blood_transfusion",
                "receiving_unsterile_injections",
                "distention_of_abdomen",
                "history_of_alcohol_consumption",
                "fluid_overload",
                "blood_in_sputum",
                "prominent_veins_on_calf",
                "palpitations",
                "painful_walking",
                "skin_peeling",
                "silver_like_dusting",
                "small_dents_in_nails",
                "inflammatory_nails",
                "blister",
                "red_sore_around_nose",
                "yellow_crust_ooze"};
        //final String symps[]={ "itching", "skin_rash", "nodal_skin_eruptions", "continuous_sneezing", "shivering", "joint_pain", "stomach_pain", "acidity", "ulcers_on_tongue", "vomiting", "burning_micturition", "spotting_urination", "fatigue", "anxiety", "cold_hands_and_feets", "mood_swings", "weight_loss", "restlessness", "lethargy", "cough", "high_fever", "sunken_eyes", "breathlessness", "sweating", "dehydration", "indigestion", "headache", "yellowish_skin", "dark_urine", "nausea", "loss_of_appetite", "pain_behind_the_eyes", "back_pain", "constipation", "abdominal_pain", "diarrhoea", "mild_fever", "yellow_urine", "yellowing_of_eyes", "swelling_of_stomach", "swelled_lymph_nodes", "malaise", "blurred_and distorted_vision", "phlegm", "throat_irritation", "redness_of_eyes", "sinus_pressure", "runny_nose", "congestion", "chest_pain", "weakness_in_limbs", "fast_heart_rate", "pain_during_bowel_movements", "pain in anal region", "bloody stool", "irritation in anus", "neck pain", "dizziness", "cramps", "bruising", "obesity", "swollen legs", "swollen blood vessels", "puffy face and eyes", "enlarged thyroid", "brittle nails", "swollen extremeties", "excessive hunger", "drying and tingling lips", "slurred speech", "knee pain", "hip joint pain", "muscle weakness", "stiff neck", "swelling joints", "spinning movements", "loss of balance", "weakness of one body side", "loss of smell", "bladder discomfort", "foul smell of urine", "continuous feel of urine", "passage of gases", "depression", "irritability", "muscle pain", "altered sensorium", "red spots over body", "belly pain", "abnormal menstruation", "dischromic  patches", "watering from eyes", "family history", "rusty sputum", "lack of concentration", "visual disturbances", "receiving blood transfusion", "receiving_unsterile_injections", "distention_of_abdomen","history_of_alcohol_consumption", "fluid_overload", "blood_in_sputum", "prominent_veins_on_calf", "palpitations", "painful walking", "skin peeling", "silver like dusting", "small dents in nails", "inflammatory nails", "blister", "red sore around nose", "yellow crust ooze" };
        final String hindi[]={ "क्या आपको खुजली है?", "क्या आपको त्वचा पर दाने हैं?", "क्या आपके पास नोडल त्वचा का विस्फोट है?", "क्या आपको लगातार छींक आ रही है?", "क्या आपके पास कंपकंपी है?", "क्या आपको जोड़ों का दर्द है?", "क्या आपको पेट दर्द है?", "क्या आपको एसिडिटी है?", "क्या आपको जीभ पर अल्सर है?", "क्या आपको उल्टी है?", "क्या आपके पास जलने का जिक्र है", "क्या आपके पास पेशाब है?", "क्या आपको थकान है?", "क्या आपको चिंता है?", "क्या आपके पास ठंडे हाथ और फेट हैं?", "क्या आपके पास मिजाज है?", "क्या आपका वजन कम है?", "क्या आपको बेचैनी है?", "क्या आपके पास सुस्ती है?", "क्या आपको खांसी है?", "क्या आपको तेज बुखार है?", "क्या आपने आँखें मूँद ली हैं?", "क्या आप में दम है?", "क्या आपको पसीना आ रहा है?", "क्या आपको निर्जलीकरण है?", "क्या आपको अपच है?", "क्या आपको सिरदर्द है?", "क्या आपकी त्वचा पीली है?", "क्या आपके पास अंधेरा मूत्र है?", "क्या आपको मतली है?", "क्या आपको भूख कम लगती है?", "क्या आपको आंखों के पीछे दर्द होता है?", "क्या आपको कमर दर्द है?", "क्या आपको कब्ज है?", "क्या तुम्हे पेट में दर्द है?", "क्या आपको दस्त है?", "क्या आपको हल्का बुखार है?", "क्या आपको पीला पेशाब आता है?", "क्या आपके पास आँखों का पीलापन है?", "क्या आपको पेट में सूजन है?", "क्या आपको लिम्फ नोड्स की सूजन है?", "क्या आपको अस्वस्थता है?", "क्या आपके पास धुंधली और विकृत दृष्टि है?", "क्या आपके पास कफ है?", "क्या आपको गले में जलन है?", "क्या आपके पास आँखों की लालिमा है?", "क्या आपके पास साइनस का दबाव है?", "क्या आपको नाक बह रही है?", "क्या आपको कंजेशन है?", "क्या आपको सीने में दर्द है?", "क्या आपको अंगों में कमजोरी है?", "क्या आपके पास तेजी से हृदय गति है?", "क्या आपको मल त्याग के दौरान दर्द होता है?", "क्या आपको गुदा क्षेत्र में दर्द है?", "क्या आपके पास खूनी मल है?", "क्या आपको गुदा में जलन है?", "क्या आपको गर्दन में दर्द है?", "क्या आपको चक्कर आते हैं?", "क्या आपको ऐंठन है?", "क्या आपको चोट लगी है?", "क्या आपको मोटापा है?", "क्या आपके पैरों में सूजन है?", "क्या आपके पास रक्त वाहिकाओं में सूजन है?", "क्या आपके पास पफी चेहरा और आंखें हैं?", "क्या आपको थायराइड बढ़ गया है?", "क्या आपके पास भंगुर नाखून हैं?", "क्या आपके पास एक्स्ट्रेमेटिस में सूजन है?", "क्या आपको अत्यधिक भूख है?", "क्या आपके पास होंठ सूखने और झड़ने वाले हैं?", "क्या आपके पास भाषण धीमा है?", "क्या आपके घुटने में दर्द है?", "क्या आपको कूल्हे के जोड़ों में दर्द है?", "क्या आपको मांसपेशियों में कमजोरी है?", "क्या आपकी गर्दन सख्त है?", "क्या आपके पास सूजन वाले जोड़ हैं?", "क्या आपके पास कताई आंदोलनों हैं?", "क्या आपको संतुलन की हानि है?", "क्या आपके पास शरीर की एक तरफ की कमजोरी है?", "क्या आपको गंध का नुकसान है?", "क्या आपको मूत्राशय की तकलीफ है?", "क्या आपको पेशाब की दुर्गंध आती है?", "क्या आपको लगातार पेशाब का अहसास होता है?", "क्या आपके पास गैसों का मार्ग है?", "क्या आपको अवसाद है?", "क्या आपको चिड़चिड़ापन है?", "क्या आपको मांसपेशियों में दर्द है?", "क्या आपके पास सेंसियम है?", "क्या आपके शरीर पर लाल धब्बे हैं?", "क्या आपको पेट दर्द है?", "क्या आपको असामान्य मासिक धर्म है?", "क्या आपके पास डिस्क्रोमिक पैच हैं?", "क्या आपको आँखों से पानी आ रहा है?", "क्या आपका पारिवारिक इतिहास है?", "क्या आपके पास जंग लगी थूक है?", "क्या आपके पास एकाग्रता की कमी है?", "क्या आपको दृश्य गड़बड़ी है?", "क्या आपको रक्त आधान है?", "क्या आपके पास unsterile injection हैं?", "क्या आपको उदर की विकृति है?", "क्या आपके पास शराब की खपत का इतिहास है?", "क्या आपके पास तरल पदार्थ अधिभार है?", "क्या आपको थूक में रक्त है?", "क्या आपके पास बछड़े पर प्रमुख नसें हैं?", "क्या आपके पास तालमेल है?", "क्या आपको चलने में दर्द होता है?", "क्या आपकी त्वचा छील रही है?", "क्या आपके पास चांदी जैसी धूल है?", "क्या आपके पास नाखूनों में छोटे डेंट हैं?", "क्या आपके पास सूजन वाले नाखून हैं?", "क्या आपके पास छाला है?", "क्या आपके पास नाक के आसपास लाल रंग है?", "क्या आपके पास पीला क्रस्ट ओज है?" };
        final HashMap<String,String> hindiMap = new HashMap<>();
        HashMap<String,String> revHindi = new HashMap<>();
        for(int i=0;i<symps.length;++i){
            hindiMap.put(symps[i],hindi[i]);
            revHindi.put(hindi[i],symps[i]);
        }





        final HashMap<String,Integer> sympMap= new HashMap<>();
        int cnt=0;
        for(String x:symps){
            revMapForsymps.put(x,cnt++);
            sympMap.put(x,0);
        }
        cnt=0;


        for(String x:diseases){
            database.put(x,new HashMap<>(sympMap));
            revMapForDiseases.put(x,cnt++);
        }
        double[][] d_s = {{0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
{0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0},
{0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
{1,1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,1,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,1,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
{0,0,0,1,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
{0,1,0,0,1,1,0,0,0,1,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,1,1,1,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
{1,1,0,0,0,0,1,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
{1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
{0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
{0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
{0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
{1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1,1,0,1,0,0,0,1,0,0,1,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
{0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
{0,0,0,0,0,1,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
{0,0,0,0,0,1,0,0,0,1,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,1,1,1,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
{0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,1,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
{0,0,0,0,0,0,0,0,0,1,0,0,1,1,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0},
{0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
{0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1},
{1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,1,0,0,0,1,0,0,0,0,0,0,1,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
{0,0,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,1,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
{0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
{0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0},
{0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
{0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
{0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,1,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
{0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,0,0,0},
{0,0,0,0,1,0,0,0,0,1,0,0,1,0,0,0,1,0,0,1,1,0,1,1,0,0,0,0,0,0,1,0,0,0,0,0,1,0,1,0,1,1,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0},
{0,0,0,0,1,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
{0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
{0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0},
{0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,0,0,0,1,1,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
};


        //System.out.println(MainActivity.response.getString("Name"));

        String location1="";
        String location2="";
        String  time1="";
        String time2="";
        String   diseaseSelected="";
    String total="";

        try {
           total = MainActivity.response.getString("total");
            System.out.println(total);


           // System.out.println(MainActivity.response.getString("Name"));

            location1 = MainActivity.response.getString("location1");
            location2 = MainActivity.response.getString("location2");
            System.out.println(location1+" "+location2);
            System.out.println(MainActivity.response);
            time1 = MainActivity.response.getString("time1");
            time2 = MainActivity.response.getString("time2");
            diseaseSelected = MainActivity.response.getString("diseases");
            //d_s = merge_arrays(d_s, new double[d_s.length][4]);
            total = MainActivity.response.getString("total");
            System.out.println(Arrays.toString(d_s[0]));
            d_s[revMapForDiseases.get(diseaseSelected)][d_s[0].length-4]=Math.round(Double.parseDouble(location1)/ Double.parseDouble(total));
            d_s[revMapForDiseases.get(diseaseSelected)][d_s[0].length-3]=1-Math.round(Double.parseDouble(location1)/ Double.parseDouble(total));
            d_s[revMapForDiseases.get(diseaseSelected)][d_s[0].length-2]=Math.round(Double.parseDouble(time1)/ Double.parseDouble(total));
            d_s[revMapForDiseases.get(diseaseSelected)][d_s[0].length-1]=Math.round(Double.parseDouble(time2)/ Double.parseDouble(total));

            System.out.println( "jasdeep  "+d_s[revMapForDiseases.get(diseaseSelected)][d_s[0].length-4]+" "+d_s[revMapForDiseases.get(diseaseSelected)][d_s[0].length-3]);

        }
        catch (Exception e){
            System.out.println("Ex json");
        }
        for(int i=d_s[0].length-4;i<d_s.length;++i){

        }


        diagnostic=new Fuzzy_Predictor(d_s);
        //Main Logic End
        //Subb Start
        mListView = (ListView) view.findViewById(R.id.listView);
        mButtonSend = (FloatingActionButton) view.findViewById(R.id.btn_send);
        mEditTextMessage = (AppCompatMultiAutoCompleteTextView) view.findViewById(R.id.et_message);
        yesButt=(Button)view.findViewById(R.id.yesButt);
        noButt=(Button) view.findViewById(R.id.noButt) ;


        ArrayAdapter<String> adapter11=new ArrayAdapter<String>(view.getContext(),android.R.layout.select_dialog_item,symps);
        //mEditTextMessage.setThreshold(1);
        mEditTextMessage.setAdapter(adapter11);
        mEditTextMessage.setTokenizer(new AppCompatMultiAutoCompleteTextView.CommaTokenizer());
        mImageView = (ImageView) view.findViewById(R.id.iv_image);
        mAdapter = new ChatMessageAdapter(view.getContext(), new ArrayList<ChatMessage>());
        mListView.setAdapter(mAdapter);
        new AlertDialog.Builder(getContext())
                .setTitle("Language")
                .setMessage("Select the Language Hindi or English")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("Hindi", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        ChatBot.hindi=1;
                        mimicOtherMessage("स्वागत हे");
                        mimicOtherMessage("हाय यह वैद्य है");
                        mimicOtherMessage("कृपया यहां शुरू होने वाले कुछ लक्षण प्रदान करें");
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton("English", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ChatBot.hindi=0;
                        mimicOtherMessage("Welcome");
                        mimicOtherMessage("Hi this is Vaidya");
                        mimicOtherMessage("Please provide Some Symptoms starting with its count followed by the space and add location 1 or 2 as its end");
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();


        //mimicOtherMessage("Please Enter the Language 1(Hindi) or 0(English)");
        int count=0;
        try {
            yesButt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println(symps[prevSym] + " " + 1);
                    if (ChatBot.hindi == 1) {
                        sendMessage("हाँ");
                    } else
                        sendMessage("Yes");
                    diagnostic.reward_all(prevSym, 1);
                    if (!diagnostic.ended()) {
                        int askThisSym = diagnostic.next_symptom();
                        changePrevSymp(askThisSym);
                        if (ChatBot.hindi == 1)
                            mimicOtherMessage(hindiMap.get(symps[askThisSym]));
                        else
                            mimicOtherMessage("do you have " + symps[askThisSym] + " ?");
                        /*if(symps[askThisSym]=="fatigue")
                        {
                            mimicOtherMessage(getString(R.string.etxt));
                        }*/

                    } else {
                        ArrayList<Integer> diagDis = diagnostic.getDiagnosis(5);
                        StringBuffer sympres = new StringBuffer("");
                        for (int x : diagDis) {
                            //pn(diseases[x]+" "+jasdeepIsLazy.scores[x]);
                            sympres.append(diseases[x] + " " + diagnostic.scores[x] + "\n");
                        }
                        mimicOtherMessage(sympres.toString());

                    }
                    mEditTextMessage.setText("");
                    mListView.setSelection(mAdapter.getCount() - 1);
                }
            });
            noButt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println(symps[prevSym] + " " + 0);
                    if (ChatBot.hindi == 1)
                        sendMessage("नहीं");
                    else
                        sendMessage("No");

                    diagnostic.reward_all(prevSym, 0);
                    if (!diagnostic.ended()) {
                        int askThisSym = diagnostic.next_symptom();
                        changePrevSymp(askThisSym);
                        if (ChatBot.hindi == 1)
                            mimicOtherMessage(hindiMap.get(symps[askThisSym]));
                        else
                            mimicOtherMessage("do you have " + symps[askThisSym] + " ?");
                        /*if(symps[askThisSym]=="fatigue")
                        {
                            mimicOtherMessage(getString(R.string.etxt));
                        }*/
                    } else {
                        ArrayList<Integer> diagDis = diagnostic.getDiagnosis(5);
                        StringBuffer sympres = new StringBuffer("");
                        for (int x : diagDis) {
                            //pn(diseases[x]+" "+jasdeepIsLazy.scores[x]);
                            sympres.append(diseases[x] + " " + diagnostic.scores[x] + "\n");
                        }
                        mimicOtherMessage(sympres.toString());

                    }

                    mEditTextMessage.setText("");
                    mListView.setSelection(mAdapter.getCount() - 1);
                }
            });
            mButtonSend.setOnClickListener(new View.OnClickListener() {
                String prev;

                @Override
                public void onClick(View v) {


                    String message = mEditTextMessage.getText().toString();
                    sendMessage(message);
                    if (message.equals("Yes") || message.equals("No")) {
                        if (message.equals("Yes")) {
                            System.out.println(symps[prevSym] + " " + 1);
                            diagnostic.reward_all(prevSym, 1);
                        } else {
                            System.out.println(symps[prevSym] + " " + 0);
                            diagnostic.reward_all(prevSym, 0);
                        }
                        if (!diagnostic.ended()) {
                            int askThisSym = diagnostic.next_symptom();
                            changePrevSymp(askThisSym);
                            mimicOtherMessage("do you have " + symps[askThisSym] + " ?");
                        /*if(symps[askThisSym]=="fatigue")
                        {
                            mimicOtherMessage(getString(R.string.etxt));
                        }*/
                        } else {
                            ArrayList<Integer> diagDis = diagnostic.getDiagnosis(5);
                            StringBuffer sympres = new StringBuffer("");
                            for (int x : diagDis) {
                                //pn(diseases[x]+" "+jasdeepIsLazy.scores[x]);
                                sympres.append(diseases[x] + " " + diagnostic.scores[x] + "\n");
                            }
                            mimicOtherMessage(sympres.toString());

                        }


                    } else {

                        String[] tempSymp = message.split(", ");
                        System.out.println(message);

                        int[] got_n = new int[tempSymp.length - 1];
                        int i = 0;
                        for (i = 0; i < tempSymp.length - 1; ++i) {
                            got_n[i] = revMapForsymps.get(tempSymp[i]);

                        }

                        diagnostic.initial_symptoms(got_n, Integer.parseInt(tempSymp[tempSymp.length - 1]), 1);
                        int askThisSym = diagnostic.next_symptom();
                        mimicOtherMessage("do you have " + symps[askThisSym] + " ?");
                   /* if(symps[askThisSym]=="fatigue")
                    {
                        mimicOtherMessage(getString(R.string.etxt));
                    }*/
                        changePrevSymp(askThisSym);

                    }
                    //sendMessage(message);

                    mEditTextMessage.setText("");
                    mListView.setSelection(mAdapter.getCount() - 1);


                }

            });
        }
        catch (Exception e)
        {
            Toast.makeText(getContext(), "onClick", Toast.LENGTH_LONG).show();
        }
        //Subb End

    }

    public static double[][] merge_arrays(double a[][],double b[][])
    {
        double c[][]=new double[a.length][a[0].length+b[0].length];
        for(int i=0;i<a.length;i++)
        {
            for(int j=0;j<a[0].length;j++)
                c[i][j]=a[i][j];
            for(int j=a[0].length;j<a[0].length+b[0].length;j++)
                c[i][j]=b[i][j-a[0].length];
        }
        return c;
    }
    private void sendMessage(String message) {
        ChatMessage chatMessage = new ChatMessage(message, true, false);
        mAdapter.add(chatMessage);
        //respond as Helloworld
        //mimicOtherMessage(response);
    }
    private void mimicOtherMessage(String message) {
        ChatMessage chatMessage = new ChatMessage(message, false, false);
        mAdapter.add(chatMessage);
    }


    private void sendMessage() {
        ChatMessage chatMessage = new ChatMessage(null, true, true);
        mAdapter.add(chatMessage);

        mimicOtherMessage();
    }

    private void mimicOtherMessage() {
        ChatMessage chatMessage = new ChatMessage(null, false, true);
        mAdapter.add(chatMessage);
    }
    public void changePrevSymp(int symp)
    {
        this.prevSym=symp;
    }


}
class Android_Predictor
{
    int disease_symptoms[][];
    boolean symptom_done[];
    int winner;
    int ones[];
    double scores[];
    int end_counter;
    int question_counter;
    int matches[];
    int n_symptoms;
    Android_Predictor(int d_s[][],int ones_per_column[])
    {
        disease_symptoms = d_s;
        symptom_done = new boolean[d_s[0].length];
        winner=0;
        ones=ones_per_column;
        end_counter=0;
        question_counter=0;
        matches=new int[d_s.length];
        n_symptoms=0;
        scores=new double[d_s.length];
    }
    Android_Predictor(int d_s[][])
    {
        disease_symptoms = d_s;
        symptom_done = new boolean[d_s[0].length];
        winner=0;
        ones=new int[disease_symptoms[0].length];
        end_counter=0;
        question_counter=0;
        matches=new int[d_s.length];
        n_symptoms=0;
        scores=new double[d_s.length];
        for(int i=0;i<ones.length;i++)
        {
            for(int j=0;j<disease_symptoms.length;j++)
            {
                ones[i]+=disease_symptoms[j][i];
            }
        }
    }
    public double find_resolvability(int symptom)
    {
        double resolvability=0;
        int matchers=0;
        int non_matchers=0;
        int winner_symptom=disease_symptoms[winner][symptom];
        if(winner_symptom==1)
            matchers=ones[symptom];
        else
            matchers=disease_symptoms.length-ones[symptom];
        non_matchers=disease_symptoms.length-matchers;
        for(int i=0;i<disease_symptoms.length;i++)
        {
            if(i!=winner)
            {
                if(disease_symptoms[i][symptom]==winner_symptom)
                    resolvability-=(100.0/Math.max(matchers*(scores[winner]-scores[i]),1));
                else
                    resolvability+=(100.0/Math.max(non_matchers*(scores[winner]-scores[i]),1));
            }
        }
        return resolvability;
    }
    public void reward_all(int symptom,int result)
    {
        int matchers=0;
        n_symptoms+=1;
        int non_matchers=0;
        if(result==1)
            matchers=ones[symptom];
        else
            matchers=disease_symptoms.length-ones[symptom];
        non_matchers=disease_symptoms.length-matchers;
        for(int i=0;i<disease_symptoms.length;i++)
        {
            if(disease_symptoms[i][symptom]==result)
            {
                scores[i]+=(100.0/matchers);
                matches[i]+=1;
            }
            else
                scores[i]-=(100.0/non_matchers);
            if(scores[i]>scores[winner])
                winner=i;
        }
    }
    public int next_symptom()
    {
        question_counter+=1;
        int best_symptom=0;
        double max_resolvability=-1000000;
        double resolvability=0;
        for(int i=0;i<disease_symptoms[0].length;i++)
        {
            if(!symptom_done[i])
            {
                resolvability=find_resolvability(i);
                if(max_resolvability<resolvability)
                {
                    max_resolvability=resolvability;
                    best_symptom=i;
                }
            }
        }
        if(max_resolvability<1)
            end_counter+=1;
        else
            end_counter=0;
        symptom_done[best_symptom]=true;
        return best_symptom;
    }
    public boolean ended()
    {
        if(end_counter>=3 && question_counter>=5)
            return true;
        else
            return false;
    }
    public void initial_symptoms(int in_sys[])
    {
        for(int i=0;i<in_sys.length;i++)
        {
            reward_all(in_sys[i],1);
            symptom_done[in_sys[i]]=true;
        }
    }
    public ArrayList<Integer> getDiagnosis(int first_n){
        ArrayList<Integer> result = new ArrayList<>();

        ArrayList<pair> timePass= new ArrayList<>();
        for(int i=0;i<scores.length;++i){
            timePass.add(new pair(scores[i],i));
        }
        Collections.sort(timePass,Collections.reverseOrder());

        for(int i=0;i<first_n;++i){
            result.add(timePass.get(i).y);
        }
        return result;




    }
    static class pair implements Comparable<pair>{

        Double x;
        int y;

        pair (Double i,int j)
        { x=i; y=j;}
        public int compareTo(pair p){
            if(this.x!=p.x) {
                Double xx=this.x;
                Double xy=p.x;
                return xx.compareTo(xy);
            }
            else { return this.y-p.y;}
        }
        public int hashCode() { return (x+" "+y).hashCode();}
        public String toString(){ return x+" "+y;}
        public boolean equals(Object o){
            pair x = (pair) o ;
            return (x.x==this.x&&x.y==this.y);}
    }

}
class Fuzzy_Predictor
{
    double disease_symptoms[][];
    boolean symptom_done[];
    int winner;
    double ones[];
    double scores[];
    int end_counter;
    int question_counter;
    int matches[];
    int n_symptoms;
    Fuzzy_Predictor(double d_s[][],double ones_per_column[])
    {
        disease_symptoms = d_s;
        symptom_done = new boolean[d_s[0].length];
        winner=0;
        ones=ones_per_column;
        end_counter=0;
        question_counter=0;
        matches=new int[d_s.length];
        n_symptoms=0;
        scores=new double[d_s.length];
    }
    Fuzzy_Predictor(double d_s[][])
    {
        disease_symptoms = d_s;
        symptom_done = new boolean[d_s[0].length];
        winner=0;
        ones=new double[disease_symptoms[0].length];
        end_counter=0;
        question_counter=0;
        matches=new int[d_s.length];
        n_symptoms=0;
        scores=new double[d_s.length];
        for(int i=0;i<ones.length;i++)
        {
            for(int j=0;j<disease_symptoms.length;j++)
            {
                ones[i]+=disease_symptoms[j][i];
            }
        }
    }
    public double find_resolvability(int symptom)
    {
        double resolvability=0,p_same=0,p_different=0;
        double matchers=ones[symptom]*disease_symptoms[winner][symptom]+(disease_symptoms.length-ones[symptom])*(1-disease_symptoms[winner][symptom]);
        double non_matchers=disease_symptoms.length-matchers;
        for(int i=0;i<disease_symptoms.length;i++)
        {
            if(i!=winner)
            {
                p_same=disease_symptoms[i][symptom]*disease_symptoms[winner][symptom]+(1-disease_symptoms[i][symptom])*(1-disease_symptoms[winner][symptom]);
                //p_different=disease_symptoms[i][symptom]*(1-disease_symptoms[winner][symptom])+disease_symptoms[winner][symptom]*(1-disease_symptoms[i][symptom]);
                p_different=1-p_same;


                resolvability+= (-p_same*(100/matchers)+p_different*(100/non_matchers))
                        *(1/Math.max(scores[winner]-scores[i],1));
            }
        }
        return resolvability;
    }
    public void reward_all(int symptom,int result)
    {
        n_symptoms+=1;
        for(int i=0;i<disease_symptoms.length;i++)
        {
            if(result==1)
                scores[i]+=disease_symptoms[i][symptom]*(100.0/ones[symptom])-(1-disease_symptoms[i][symptom])*(100.0/(disease_symptoms.length-ones[symptom]));
            else
                scores[i]-=disease_symptoms[i][symptom]*(100.0/ones[symptom])-(1-disease_symptoms[i][symptom])*(100.0/(disease_symptoms.length-ones[symptom]));
            if((disease_symptoms[i][symptom]>=0.5 && result==1) || (disease_symptoms[i][symptom]<0.5 && result==0))
                matches[i]++;
            if(scores[i]>scores[winner])
            {
                winner=i;
            }
        }
    }
    public int next_symptom()
    {
        question_counter+=1;
        int best_symptom=0;
        double max_resolvability=-1000000;
        double resolvability=0;
        for(int i=0;i<disease_symptoms[0].length;i++)
        {
            if(!symptom_done[i])
            {
                resolvability=find_resolvability(i);
                if(max_resolvability<resolvability)
                {
                    max_resolvability=resolvability;
                    best_symptom=i;
                }
            }
        }
        if(max_resolvability<1)
            end_counter+=1;
        else
            end_counter=0;
        symptom_done[best_symptom]=true;
        return best_symptom;
    }
    public boolean ended()
    {
        if(end_counter>=3 && question_counter>=5)
            return true;
        else
            return false;
    }
    public void initial_symptoms(int in_sys[],int loc,int time)
    {
        for(int i=0;i<in_sys.length;i++)
        {
            reward_all(in_sys[i],1);

            symptom_done[in_sys[i]]=true;
        }
        symptom_done[disease_symptoms[0].length-4+loc]=true;
        symptom_done[disease_symptoms[0].length-2+time]=true;
    }
    public ArrayList<Integer> getDiagnosis(int first_n){
        ArrayList<Integer> result = new ArrayList<>();

        ArrayList<pair> timePass= new ArrayList<>();
        for(int i=0;i<scores.length;++i){
            timePass.add(new pair(scores[i],i));
        }
        Collections.sort(timePass,Collections.reverseOrder());

        for(int i=0;i<first_n;++i){
            result.add(timePass.get(i).y);
        }
        return result;




    }
    static class pair implements Comparable<pair>{

        Double x;
        int y;

        pair (Double i,int j)
        { x=i; y=j;}
        public int compareTo(pair p){
            if(this.x!=p.x) {
                Double xx=this.x;
                Double xy=p.x;
                return xx.compareTo(xy);
            }
            else { return this.y-p.y;}
        }
        public int hashCode() { return (x+" "+y).hashCode();}
        public String toString(){ return x+" "+y;}
        public boolean equals(Object o){
            pair x = (pair) o ;
            return (x.x==this.x&&x.y==this.y);}
    }

}

