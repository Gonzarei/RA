package com.software.realidadaumentada;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.assets.RenderableSource;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class MainActivity extends AppCompatActivity {


    private ArFragment arFragment;
    private ModelRenderable renderable =null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.arFrament);
        Descargar("https://firebasestorage.googleapis.com/v0/b/sistema-a2c0b.appspot.com/o/INODORO%20BABY%20FRESH.glb?alt=media&token=f2a6c497-5745-4e61-9302-7e760055b21d");
        arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) ->{

            if (renderable==null){
                return;
            }

            Anchor anchor = hitResult.createAnchor();
            AnchorNode anchorNode = new  AnchorNode(anchor);
            anchorNode.setParent(arFragment.getArSceneView().getScene());
            TransformableNode node = new TransformableNode(arFragment.getTransformationSystem());

            node.setParent(anchorNode);
            node.setRenderable(renderable);
            node.select();
        });

    }

    private void Descargar(String url){
        RenderableSource renderableSource = RenderableSource.builder().setSource(this, Uri.parse(url),RenderableSource.SourceType.GLB)
                .setRecenterMode(RenderableSource.RecenterMode.CENTER)
                .build();

        ModelRenderable.builder()
                .setSource(this, renderableSource)
                .build()
                .thenAccept( modelRenderable -> {
                    renderable  = modelRenderable;
                    Toast.makeText(getApplicationContext(),"Objeto descargado",Toast.LENGTH_SHORT).show();
                })
                .exceptionally( throwable ->  {Toast.makeText(getApplicationContext(),"No se pudo descagar",Toast.LENGTH_SHORT).show();
                    return null;
                });
    }
}