
package org.tempuri;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de TipoAtividade.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * <p>
 * <pre>
 * &lt;simpleType name="TipoAtividade">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="NaoInformado"/>
 *     &lt;enumeration value="CNAE"/>
 *     &lt;enumeration value="Auxiliar"/>
 *     &lt;enumeration value="Todas"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TipoAtividade")
@XmlEnum
public enum TipoAtividade {

    @XmlEnumValue("NaoInformado")
    NAO_INFORMADO("NaoInformado"),
    CNAE("CNAE"),
    @XmlEnumValue("Auxiliar")
    AUXILIAR("Auxiliar"),
    @XmlEnumValue("Todas")
    TODAS("Todas");
    private final String value;

    TipoAtividade(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TipoAtividade fromValue(String v) {
        for (TipoAtividade c: TipoAtividade.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
