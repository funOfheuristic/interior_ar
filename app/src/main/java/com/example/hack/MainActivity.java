package com.example.hack;

//import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.collision.CollisionShape;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.Material;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ArFragment arFragment;
    private ModelRenderable fileRenderable, chariRenderable;
    private List<ModelRenderable> assetsFiles = new ArrayList<>();

    ImageView bedroom, chair, buddha, couch, newsofa;

    View arrayView[];
    ViewRenderable name_file;

    int selected = 0;

    int selectedColor = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        arFragment = (ArFragment)getSupportFragmentManager().findFragmentById(R.id.sceneform_ux_fragment);

        bedroom = (ImageView)findViewById(R.id.bedroom);
        chair = (ImageView)findViewById(R.id.chair);
        buddha = (ImageView)findViewById(R.id.buddha);
        couch = (ImageView)findViewById(R.id.couch);
        newsofa = (ImageView)findViewById(R.id.newsofa);

        setArrayView();

        setClickListner();

        setupModel();

        arFragment.setOnTapArPlaneListener(new BaseArFragment.OnTapArPlaneListener() {
            @Override
            public void onTapPlane(HitResult hitResult, Plane plane, MotionEvent motionEvent) {
                //when user tab on plan a model will be added

                    Anchor anchor = hitResult.createAnchor();
                    AnchorNode anchorNode = new AnchorNode(anchor);
                    anchorNode.setParent(arFragment.getArSceneView().getScene());

                    createModel(anchorNode, selected, selectedColor);


            }
        });
    }

    private void setupModel() {
        List<String> filesUrls = new ArrayList<>();


        filesUrls.add("http://10.2.41.84:4200/assets/chair.sfb");
        filesUrls.add("http://10.2.41.84:4200/assets/newSofa.sfb");
        filesUrls.add("http://10.2.41.84:4200/assets/Couch.sfb");
        filesUrls.add("http://10.2.41.84:4200/assets/buddha.sfb");
        filesUrls.add("http://10.2.41.84:4200/assets/Bedroom.sfb");



        for(int i = 0; i < filesUrls.size(); i++){
            ModelRenderable.builder()
                    .setSource(this,Uri.parse(filesUrls.get(i)))
                    .build().thenAccept(renderable -> assetsFiles.add(renderable))
                    .exceptionally(
                            throwable -> {
                                Toast.makeText(this, "Unable to load file model", Toast.LENGTH_SHORT).show();
                                return null;
                            }
                    );
        }



//        ModelRenderable.builder()
//                .setSource(this,R.raw.file)
//                .build().thenAccept(renderable -> fileRenderable = renderable)
//                .exceptionally(
//                        throwable -> {
//                            Toast.makeText(this, "Unable to load file model", Toast.LENGTH_SHORT).show();
//                            return null;
//                        }
//                );
//
//        ModelRenderable.builder()
//                .setSource(this, Uri.parse("http://10.2.41.84:4200/assets/chair.sfb"))
//                .build().thenAccept(renderable -> chariRenderable = renderable)
//                .exceptionally(
//                        throwable -> {
//                            Toast.makeText(this, "Unable to load file model", Toast.LENGTH_SHORT).show();
//                            return null;
//                        }
//                );

    }

    private void createModel(AnchorNode anchorNode, int selected, int selectedColor) {
//        if(selected == 0){
//            TransformableNode file = new TransformableNode(arFragment.getTransformationSystem());
//            file.setParent(anchorNode);
//            Toast.makeText(this, "selected is"+assetsFiles.get(selected), Toast.LENGTH_SHORT).show();
//            file.setRenderable(assetsFiles.get(selected));
//
//            file.select();
//        }else if(selected == 1){
//            TransformableNode chair = new TransformableNode(arFragment.getTransformationSystem());
//            chair.setParent(anchorNode);
//            Toast.makeText(this, "selected is"+assetsFiles.get(selected), Toast.LENGTH_SHORT).show();
//            chair.setRenderable(assetsFiles.get(selected));
//
//            chair.select();
//        }

//        CollisionShape c = assetsFiles.get(selected).getCollisionShape();
        if(selectedColor == 1){
            Material changedMaterial = assetsFiles.get(selected).getMaterial().makeCopy();
            changedMaterial.setFloat3("baseColor", new Color(android.graphics.Color.rgb(153,204,0)));
            assetsFiles.get(selected).setMaterial(changedMaterial);
        }else if(selectedColor == 2){
            Material changedMaterial = assetsFiles.get(selected).getMaterial().makeCopy();
            changedMaterial.setFloat3("baseColor", new Color(android.graphics.Color.rgb(0,0,0)));
            assetsFiles.get(selected).setMaterial(changedMaterial);
        }else if(selectedColor == 3){
            Material changedMaterial = assetsFiles.get(selected).getMaterial().makeCopy();
            changedMaterial.setFloat3("baseColor", new Color(android.graphics.Color.rgb(170,170,170)));
            assetsFiles.get(selected).setMaterial(changedMaterial);
        }

        if(selected >= 0 && selected < assetsFiles.size()){
            TransformableNode file = new TransformableNode(arFragment.getTransformationSystem());
            file.setParent(anchorNode);
            Toast.makeText(this, "selected is"+selected, Toast.LENGTH_SHORT).show();

            file.setRenderable(assetsFiles.get(selected));

            file.select();
        }


    }

    private void setClickListner() {
        for(int i=0; i < arrayView.length; i++){
            arrayView[i].setOnClickListener(this);
        }
    }

    private void setArrayView() {
        arrayView = new View[]{
            chair,newsofa, couch, buddha,bedroom
        };
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, "selected 1"+v.getId(), Toast.LENGTH_SHORT).show();
        if(v.getId() == R.id.chair){
            selected = 4;
//            Toast.makeText(this, "selected 0"+selected, Toast.LENGTH_SHORT).show();
            setBackground(v.getId());
        }
        else if(v.getId() == R.id.newsofa){
            selected = 0;
//            Toast.makeText(this, "selected 1"+selected, Toast.LENGTH_SHORT).show();
            setBackground(v.getId());
        }else if(v.getId() == R.id.couch){
            selected = 1;
//            Toast.makeText(this, "selected 1"+selected, Toast.LENGTH_SHORT).show();
            setBackground(v.getId());
        }
        else if(v.getId() == R.id.buddha){
            selected = 3;
//            Toast.makeText(this, "selected 1"+selected, Toast.LENGTH_SHORT).show();
            setBackground(v.getId());
        }
        else if(v.getId() == R.id.bedroom){
            selected = 2;
//            Toast.makeText(this, "selected 1"+selected, Toast.LENGTH_SHORT).show();
            setBackground(v.getId());
        }
    }

    private void setBackground(int id) {
        for(int i=0; i < arrayView.length; i++){
            if(arrayView[i].getId() == id){
                arrayView[i].setBackgroundColor(android.graphics.Color.rgb(0,0,0));
                arrayView[i].getBackground().setAlpha(100);
            }else{
                arrayView[i].setBackgroundColor(android.graphics.Color.TRANSPARENT);
            }
        }
    }

    public void setColour(View v)
    {
        if(v.getId() == R.id.green){
            Toast.makeText(this, "Green Selected", Toast.LENGTH_SHORT).show();
            selectedColor = 1;
        }else if(v.getId() == R.id.black){
            Toast.makeText(this, "Black Selected", Toast.LENGTH_SHORT).show();
            selectedColor = 2;
        }else if(v.getId() == R.id.gray){
            Toast.makeText(this, "Gray Selected", Toast.LENGTH_SHORT).show();
            selectedColor = 3;
        }
    }
}
