package com.idea3d.calculina

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration.TAG_FOR_CHILD_DIRECTED_TREATMENT_TRUE
import com.idea3d.calculina.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var num1:Double=0.0
    private var num2:Double=0.0
    private var operacion:Int= 0
    private var resultade:Int=0

    var requestConfiguration = MobileAds.getRequestConfiguration()
        .toBuilder()
        .setTagForChildDirectedTreatment(TAG_FOR_CHILD_DIRECTED_TREATMENT_TRUE)
        .build()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        MobileAds.initialize(this)
        MobileAds.setRequestConfiguration(requestConfiguration)

        initLoadAds()

        binding.unoBoton.setOnClickListener{numeroPresionado("1")}
        binding.dosBoton.setOnClickListener{numeroPresionado("2")}
        binding.tresBoton.setOnClickListener{numeroPresionado("3")}
        binding.cuatroBoton.setOnClickListener{numeroPresionado("4")}
        binding.cincoBoton.setOnClickListener{numeroPresionado("5")}
        binding.seisBoton.setOnClickListener{numeroPresionado("6")}
        binding.sieteBoton.setOnClickListener{numeroPresionado("7")}
        binding.ochoBoton.setOnClickListener{numeroPresionado("8")}
        binding.nueveBoton.setOnClickListener{numeroPresionado("9")}
        binding.ceroBoton.setOnClickListener{ numeroPresionado("0")}
        binding.comaBoton.setOnClickListener{ numeroPresionado(".")}


        binding.sumarBoton.setOnClickListener{operacionPresionada(1)}
        binding.restaBoton.setOnClickListener{operacionPresionada(2)}
        binding.multiplicarBoton.setOnClickListener{operacionPresionada(3)}
        binding.dividirBoton.setOnClickListener{operacionPresionada(4)}

        binding.visorCalculadora.text= "0"


        binding.clearBoton.setOnClickListener{
            num1=0.0
            num2=0.0
            binding.visorCalculadora.text= "0"
            operacion= NO_OPERACION
        }

        binding.igualBoton.setOnClickListener{
            var resultado = when (operacion){
                SUMA->num1+num2
                RESTA->num1-num2
                MULTIPLICAR-> num1*num2
                DIVIDIR -> num1/num2
                else ->0
            }

            if (resultado.toString().last() =='0'){
                resultade=resultado.toInt()
                binding.visorCalculadora.text=resultade.toString()
            }else {
            binding.visorCalculadora.text=resultado.toString()}

            num1= resultado.toDouble()
        }


    }

    private fun numeroPresionado (digito:String){

        if (binding.visorCalculadora.text=="0" && digito!="." ){
            binding.visorCalculadora.text = "$digito"
        }else {
            binding.visorCalculadora.text = "${binding.visorCalculadora.text}$digito"
        }

        if(operacion== NO_OPERACION){
            num1= binding.visorCalculadora.text.toString().toDouble()
        }else{
            num2 = binding.visorCalculadora.text.toString().toDouble()
        }

        var mediaPlayer: MediaPlayer? = MediaPlayer.create(this, R.raw.sonidotres)
        mediaPlayer?.start()

    }

    private fun operacionPresionada (operacion:Int) {
        this.operacion = operacion
        if(num1==0.0 && num2==0.0&& this.operacion!=2 && binding.visorCalculadora.text =="0"){
            this.operacion= NO_OPERACION
        }else if (num1==0.0 && num2==0.0 && this.operacion==2 && binding.visorCalculadora.text=="0"){
            this.operacion= NO_OPERACION
            binding.visorCalculadora.text="-"
        }else{

        binding.visorCalculadora.text = "0"
        }
    }

    companion object{
        const val SUMA = 1
        const val RESTA = 2
        const val MULTIPLICAR = 3
        const val DIVIDIR = 4
        const val NO_OPERACION = 0
    }

    private fun initLoadAds(){
        val adRequest: AdRequest =AdRequest.Builder()
            .build()
        binding.banner.loadAd(adRequest)
    }






}