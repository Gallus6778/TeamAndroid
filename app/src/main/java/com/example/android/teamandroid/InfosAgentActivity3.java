package com.example.android.teamandroid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;

public class InfosAgentActivity3 extends AppCompatActivity {

    private TextView etape_1;
    private TextView etape_2;
    private TextView etape_3;
    private ImageView link_to_end_step;

    ImageView prendre_photo;
    ImageView photo_prise_2;

    Uri imageAffiche;

    private BottomNavigationItemView navigation_dashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infos_agent3);

// ==================================================================================================
        // lien vers la page etape 1
        this.etape_1 = (TextView) findViewById(R.id.etape_1);

        etape_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity = new Intent(getApplicationContext(), TakePictureActivity.class);
                startActivity(otherActivity);
                finish();
            }
        });
// ==================================================================================================
        // lien vers la page etape 2
        this.etape_2 = (TextView) findViewById(R.id.etape_2);

        etape_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity = new Intent(getApplicationContext(), InfosAgentActivity.class);
                startActivity(otherActivity);
                finish();
            }
        });
// ==================================================================================================
        // lien vers la page etape 3
        this.etape_3 = (TextView) findViewById(R.id.etape_3);

        etape_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity = new Intent(getApplicationContext(), InfosAgentActivity2.class);
                startActivity(otherActivity);
                finish();
            }
        });
// ==================================================================================================

        // lien vers la page validation etapes
        this.link_to_end_step = (ImageView) findViewById(R.id.link_to_end_step);

        link_to_end_step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity = new Intent(getApplicationContext(), EndInfosAgentActivity.class);
                startActivity(otherActivity);
                finish();
            }
        });

// ==================================================================================================

        // Prise de photo
        prendre_photo=findViewById(R.id.prendre_photo);
        photo_prise_2=findViewById(R.id.photo_prise_2);

        // Utiliser la camera du telephone au click sur le bouton Recto
        prendre_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED ||
                            checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
                        String[]tabPermission={Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
                        requestPermissions(tabPermission, 100);
                    }else{
                        OpenCamera();
                    }
                }else
                    OpenCamera();
            }


        });

        // Utiliser la camera du telephone au click sur le bouton verso
        photo_prise_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED ||
                            checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
                        String[]tabPermission={Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
                        requestPermissions(tabPermission, 100);
                    }else{
                        OpenCamera();
                    }
                }else
                    OpenCamera();
            }


        });
// ==================================================================================================
        // Lien pour lister les agents
        this.navigation_dashboard = (BottomNavigationItemView) findViewById(R.id.navigation_dashboard);

        navigation_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity = new Intent(getApplicationContext(), ListAgentActivity.class);
                startActivity(otherActivity);
                finish();
            }
        });

    }
    //Methode permettant l'activation de la camera
    private void OpenCamera() {
        ContentValues cv = new ContentValues();
        cv.put(MediaStore.Images.Media.TITLE, "Image obtenue");
        cv.put(MediaStore.Images.Media.DESCRIPTION, "Description image obtenue");
        imageAffiche = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cv);

        Intent CameraIntent = new Intent((MediaStore.ACTION_IMAGE_CAPTURE));
        CameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageAffiche);
        startActivityForResult(CameraIntent, 101);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==100){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                OpenCamera();
            else
                Toast.makeText(this, "Permission manquante ", Toast.LENGTH_SHORT).show();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==101) {
            prendre_photo.setImageURI(imageAffiche);
            photo_prise_2.setImageURI(imageAffiche);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}