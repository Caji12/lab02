package cr.ac.una.myapplication

class JuegoService {
    val matriz = Array(3) { CharArray(3) }
    var turnoX: Boolean = true

    /**
     * @return "X gana", "O gana", "Empate", "Sigue X", "Sigue O"
     */
    fun realizarJugada(x: Int, y: Int): String {
        if (matriz[x][y] == ' ') {
            matriz[x][y] = if (turnoX) 'X' else 'O'
            turnoX = !turnoX
            return "Sigue ${if (turnoX) 'X' else 'O'}"
        }
        return "Casilla ocupada, intente de nuevo."
    }

    fun verificarGanador(): String {
        // Verificar filas y columnas
        for (i in 0 until 3) {
            if (matriz[i][0] != ' ' && matriz[i][0] == matriz[i][1] && matriz[i][1] == matriz[i][2]) {
                return "${matriz[i][0]} gana"
            }
            if (matriz[0][i] != ' ' && matriz[0][i] == matriz[1][i] && matriz[1][i] == matriz[2][i]) {
                return "${matriz[0][i]} gana"
            }
        }

        // Verificar diagonales
        if (matriz[0][0] != ' ' && matriz[0][0] == matriz[1][1] && matriz[1][1] == matriz[2][2]) {
            return "${matriz[0][0]} gana"
        }
        if (matriz[0][2] != ' ' && matriz[0][2] == matriz[1][1] && matriz[1][1] == matriz[2][0]) {
            return "${matriz[0][2]} gana"
        }

        // Verificar empate
        if (tableroCompleto()) {
            return "Empate"
        }

        // Continuar el juego
        return ""
    }

    private fun tableroCompleto(): Boolean {
        for (fila in matriz) {
            for (casilla in fila) {
                if (casilla == ' ') {
                    return false
                }
            }
        }
        return true
    }

    fun reiniciarJuego() {
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                matriz[i][j] = ' '
            }
        }
        turnoX = true
    }
}

