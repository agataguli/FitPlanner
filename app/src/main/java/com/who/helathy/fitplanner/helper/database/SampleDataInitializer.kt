package com.who.helathy.fitplanner.helper.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.who.helathy.fitplanner.domain.Sport
import java.util.Arrays
import java.util.logging.Logger


class SampleDataInitializer() {
    companion object {
        private val LOGGER = Logger.getLogger(SampleDataInitializer::class.java.name)


        fun importSampleData(db: SQLiteDatabase) {
            this.importSampleSportData(DatabaseHelper.getContext(), db)
        }

        private val sampleSports: List<Sport> = Arrays.asList(
                Sport("Jazda konna", "https://polki.pl/foto/4_3_LARGE/jazda-konna-czyli-sport-i-terapia-w-jednym-1605089.jpg", 258),
                Sport("Aerobik", "https://radzyninfo.pl/wp-content/uploads/2017/02/aerobik_1-658x439.jpg", 300),
                Sport("Spacer", "https://radzyninfo.pl/wp-content/uploads/2017/02/aerobik_1-658x439.jpg", 126),
                Sport("Jazda rowerem", "http://fitness-inspiracje.pl/wp-content/uploads/2016/04/korzysci_z_jazdy_na_rowerze_01.jpg",210),
                Sport("Taniec", "https://upload.wikimedia.org/wikipedia/commons/3/38/Two_dancers.jpg", 256),
                Sport("Pływanie", "https://polki.pl/pub/wieszjak/p/_wspolne/pliki_infornext/246000/plywak.jpg", 268),
                Sport("Piłka nożna", "https://cdn.galleries.smcloud.net/t/galleries/gf-5RVK-iHmE-aWYd_pilka-nozna-historia-zasady-rodzaje-rozgrywek-najlepsi-zawodnicy-664x442-nocrop.jpg", 278),
                Sport("Crossfit", "http://i.blogs.es/e97394/tireflip/650_1200.jpg", 489)
        )

        private fun importSampleSportData(context: Context, db: SQLiteDatabase) {
            LOGGER.info("### Import of sample data started. ###")
            this.sampleSports.forEach { sport -> DatabaseHelper.getInstance(context)!!.addSport(sport, db) }
        }
    }


}