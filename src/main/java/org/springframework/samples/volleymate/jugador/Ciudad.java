package org.springframework.samples.volleymate.jugador;

public enum Ciudad {
    A_CORUNA("A Coruña"),
    ALAVA("Álava"),
    ALBACETE("Albacete"),
    ALICANTE("Alicante"),
    ALMERIA("Almería"),
    ASTURIAS("Asturias"),
    AVILA("Ávila"),
    BADAJOZ("Badajoz"),
    BARCELONA("Barcelona"),
    BURGOS("Burgos"),
    CACERES("Cáceres"),
    CADIZ("Cádiz"),
    CANTABRIA("Cantabria"),
    CASTELLON("Castellón"),
    CEUTA("Ceuta"),
    CIUDAD_REAL("Ciudad Real"),
    CORDOBA("Córdoba"),
    CUENCA("Cuenca"),
    GIRONA("Girona"),
    GRANADA("Granada"),
    GUADALAJARA("Guadalajara"),
    GUIPUZCOA("Guipúzcoa"),
    HUELVA("Huelva"),
    HUESCA("Huesca"),
    ILLES_BALEARS("Illes Balears"),
    JAEN("Jaén"),
    LA_RIOJA("La Rioja"),
    LAS_PALMAS("Las Palmas"),
    LEON("León"),
    LLEIDA("Lleida"),
    LUGO("Lugo"),
    MADRID("Madrid"),
    MALAGA("Málaga"),
    MELILLA("Melilla"),
    MURCIA("Murcia"),
    NAVARRA("Navarra"),
    OURENSE("Ourense"),
    PALENCIA("Palencia"),
    PONTEVEDRA("Pontevedra"),
    SALAMANCA("Salamanca"),
    SANTA_CRUZ_DE_TENERIFE("Santa Cruz de Tenerife"),
    SEGOVIA("Segovia"),
    SEVILLA("Sevilla"),
    SORIA("Soria"),
    TARRAGONA("Tarragona"),
    TERUEL("Teruel"),
    TOLEDO("Toledo"),
    VALENCIA("Valencia"),
    VALLADOLID("Valladolid"),
    VIZCAYA("Vizcaya"),
    ZAMORA("Zamora"),
    ZARAGOZA("Zaragoza");
    
        private final String nombre;
    
        private Ciudad(String nombre) {
            this.nombre = nombre;
        }
    
        public String getNombre() {
            return nombre;
        }
    
}
