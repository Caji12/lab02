package cr.ac.una.myapplication

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    lateinit var jugar: Button
    lateinit var botones: List<ImageButton>
    var juegoService = JuegoService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        jugar = findViewById(R.id.jugar)
        botones = listOf(
            findViewById(R.id.pos1),
            findViewById(R.id.pos2),
            findViewById(R.id.pos3),
            findViewById(R.id.pos4),
            findViewById(R.id.pos5),
            findViewById(R.id.pos6),
            findViewById(R.id.pos7),
            findViewById(R.id.pos8),
            findViewById(R.id.pos9)
        )

        jugar.setOnClickListener {
            iniciarJuego()
        }

        botones.forEachIndexed { index, button ->
            button.setOnClickListener {
                realizarJugada(index)
                seleccionafigura(button)
            }
        }

        enableDisableBotones(false)
    }
    private fun seleccionafigura(imageButton: ImageButton) {
        if (juegoService.turnoX)
            imageButton.setBackgroundResource(R.drawable.circulo)
        else
            imageButton.setBackgroundResource(R.drawable.cruz)
        imageButton.isEnabled = false
    }

    private fun iniciarJuego() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder
            .setMessage("¿Desea iniciar el juego?")
            .setPositiveButton("Sí") { _, _ ->
                juegoService.reiniciarJuego()
                mostrarMensaje(juegoService.realizarJugada(0, 0))
                enableDisableBotones(true)
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun realizarJugada(index: Int) {
        val x = index / 3
        val y = index % 3

        val mensaje = juegoService.realizarJugada(x, y)
        mostrarMensaje(mensaje)

        val ganador = juegoService.verificarGanador()
        if (ganador.isNotEmpty()) {
            mostrarMensaje(ganador)

            enableDisableBotones(false)
        }
    }

    private fun enableDisableBotones(enable: Boolean) {
        botones.forEach { it.isEnabled = enable }
    }

    private fun mostrarMensaje(mensaje: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder
            .setMessage(mensaje)
            .setPositiveButton("¡Entendido!") { dialog, _ ->
                dialog.dismiss()
            }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private val posicionesGanadoras = listOf(
        listOf(0, 1, 2), // Primera fila
        listOf(3, 4, 5), // Segunda fila
        listOf(6, 7, 8), // Tercera fila
        listOf(0, 3, 6), // Primera columna
        listOf(1, 4, 7), // Segunda columna
        listOf(2, 5, 8), // Tercera columna
        listOf(0, 4, 8), // Diagonal de izquierda a derecha
        listOf(2, 4, 6)  // Diagonal de derecha a izquierda
    )

    private fun actualizarImagenesGanadoras(ganador: Char) {
        val imagenGanadora: Int = when (ganador) {
            'X' -> R.drawable.cruz_roja
            'O' -> R.drawable.circulo_rojo
            else -> return // No hay ganador
        }

        posicionesGanadoras.forEach { posicionGanadora ->
            posicionGanadora.forEach { indice ->
                botones[indice].setImageResource(imagenGanadora)
                botones[indice].isEnabled = false
            }
        }
    }

}

