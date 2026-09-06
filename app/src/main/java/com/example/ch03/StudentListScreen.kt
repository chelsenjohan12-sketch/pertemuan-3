package com.example.ch03

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ch03.ui.theme.Ch03Theme
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.material3.FilterChip

data class Mahasiswa(val nama: String, val nim: String, val ipk: Double)

val dummyMahasiswa = listOf(
    Mahasiswa("Ali Rahman", "22001", 3.85),
    Mahasiswa("Budi Santoso", "22002", 3.40),
    Mahasiswa("Cici Wulandari", "22003", 3.92),
    Mahasiswa("Dian Pratama", "22004", 2.95),
    Mahasiswa("Eka Fitriani", "22005", 3.75),
    Mahasiswa("Fandi Ahmad", "22006", 3.50),
    Mahasiswa("Gita Permata", "22007", 3.88),
    Mahasiswa("Hendra Kusuma", "22008", 2.80),
    Mahasiswa("Indah Lestari", "22009", 3.65),
    Mahasiswa("Joko Pratama", "22010", 3.20),

    Mahasiswa("Shelly Purwanto", "22001", 3.65),
    Mahasiswa("Roki Kurniawan", "22002", 3.53),
    Mahasiswa("Owen Rashid", "22003", 3.95),
    Mahasiswa("Keysha Putri", "22004", 2.45),
    Mahasiswa("Dewa Ayu", "22005", 3.05),
    Mahasiswa("Kanjeng Ratu", "22006", 3.13),
    Mahasiswa("Andi Tandiwijaya", "22007", 2.88),
    Mahasiswa("Sena Ali", "22008", 2.86),
    Mahasiswa("Davina May", "22009", 3.35),
    Mahasiswa("Novan Pratama", "22010", 2.20)
)

@Composable
fun StudentListScreen() {
    DaftarMahasiswa(mahasiswaList = dummyMahasiswa)
}

@Composable
fun DaftarMahasiswa(
    mahasiswaList: List<Mahasiswa>,
    modifier: Modifier = Modifier
) {
    val category = listOf("Semua", "IPK < 3.5", "IPK >=3.5")
    var seleksi by remember {mutableStateOf("Semua")}

    val filter_siswa = when (seleksi) {
        "IPK >= 3.5" -> mahasiswaList.filter {it.ipk >= 3.5}
        "IPK < 3.5" -> mahasiswaList.filter {it.ipk < 3.5}
        else -> mahasiswaList
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                items(category.size) { index ->
                    val selectedCategory = category[index]
                    FilterChip(
                        selected = (selectedCategory == seleksi),
                        onClick = { seleksi = selectedCategory},
                        label = { Text(selectedCategory) }
                    )
                }
            }
        }

        item {
            Text(
                text = "Mahasiswa UPH Angkatan 2024",
                style = MaterialTheme.typography.headlineSmall
            )
        }

        items(
            items = filter_siswa,
            key = { it.nim }
        ) { mahasiswa ->
            MahasiswaCard(mahasiswa)
        }

        item {
            Text(
                text = "Total Mahasiswa Angkatan 2024: ${filter_siswa.size}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun MahasiswaCard(mahasiswa: Mahasiswa) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = mahasiswa.nama,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = mahasiswa.nim,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                text = "IPK ${mahasiswa.ipk}",
                style = MaterialTheme.typography.labelLarge,
                color = if (mahasiswa.ipk >= 3.5) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurface
                }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun StudentListScreenPreview() {
    Ch03Theme {
        StudentListScreen()
    }
}
