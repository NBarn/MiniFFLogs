package MiniFFLogs;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.LinkedList;

public class ReadXML extends DefaultHandler {

    private boolean ally;
    private boolean name;
    private boolean date;
    private boolean duration;
    private boolean dmg_perc;
    private boolean heal_perc;
    private boolean dps;
    private boolean hps;
    private boolean deaths;
    private boolean crit_heal;
    private boolean crit_hit;
    private boolean job;
    private boolean overheal;
    private boolean dh;
    private boolean crit_direct;

    private String boss;
    private Player player;
    private LinkedList<Player> list;
    private boolean isPlayer;
    private StringBuilder data;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {
        if (qName.equalsIgnoreCase("EncounterTable")) {
            boss = attributes.getValue("Name").split(" ")[0];
            list = new LinkedList<>();
        } else if (qName.equalsIgnoreCase("Ally")) {
            ally = true;
        } else if (qName.equalsIgnoreCase("Name")) {
            name = true;
        } else if (qName.equalsIgnoreCase("EndTime")) {
            date = true;;
        } else if (qName.equalsIgnoreCase("Duration")) {
            duration = true;
        } else if (qName.equalsIgnoreCase("DamagePerc")) {
            dmg_perc = true;
        } else if (qName.equalsIgnoreCase("HealedPerc")) {
            heal_perc = true;
        } else if (qName.equalsIgnoreCase("EncDPS")) {
            dps = true;
        } else if (qName.equalsIgnoreCase("EncHPS")) {
            hps = true;
        } else if (qName.equalsIgnoreCase("Deaths")) {
            deaths = true;
        } else if (qName.equalsIgnoreCase("CritDamPerc")) {
            crit_hit = true;
        } else if (qName.equalsIgnoreCase("CritHealPerc")) {
            crit_heal = true;
        } else if (qName.equalsIgnoreCase("Job")) {
            job = true;
        } else if (qName.equalsIgnoreCase("OverHealPct")) {
            overheal = true;
        } else if (qName.equalsIgnoreCase("DirectHitPct")) {
            dh = true;
        } else if (qName.equalsIgnoreCase("CritDirectHitPct")) {
            crit_direct = true;
        }

        data = new StringBuilder();
    }

    //TODO: Clean this
    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if (isPlayer) {
            if (ally) {
                ally = false;
            } else if (name) {
                if (data.toString().equalsIgnoreCase("YOU")) {
                    player.setFirstName("Val Guerra");
                } else {
                    player.setFirstName(data.toString().replace("'", "''"));
                }
                name = false;
            } else if (date){
                player.setDate(data.toString());
                date = false;
            } else if (duration){
                player.setDuration(Integer.parseInt(data.toString()));
                duration = false;
            } else if (dmg_perc) {
                player.setDmg_perc(Float.parseFloat(data.toString().replace("%", "")));
                dmg_perc = false;
            } else if (heal_perc) {
                player.setHealing(Float.parseFloat(data.toString().replace("%", "")));
                heal_perc = false;
            } else if (dps) {
                player.setDps(Float.parseFloat(data.toString().replace("%", "")));
                dps = false;
            } else if (hps) {
                player.setHps(Float.parseFloat(data.toString().replace("%", "")));
                hps = false;
            } else if (deaths) {
                player.setDeaths(Integer.parseInt(data.toString()));
                deaths = false;
            } else if (crit_heal) {
                player.setCrit_heal_perc(Float.parseFloat(data.toString().replace("%", "")));
                crit_heal = false;
            } else if (crit_hit) {
                player.setCrit_hit_perc(Float.parseFloat(data.toString().replace("%", "")));
                crit_hit = false;
            } else if (job) {
                player.setJob(data.toString());
                job = false;
            } else if (overheal) {
                player.setOverheal_perc(Float.parseFloat(data.toString().replace("%", "")));
                overheal = false;
            } else if (dh) {
                player.setDh_perc(Float.parseFloat(data.toString().replace("%", "")));
                dh = false;
            } else if (crit_direct) {
                player.setCrit_dh_perc(Float.parseFloat(data.toString().replace("%", "")));
                crit_direct = false;
            }
        } else {
            if (ally) {
                ally = false;
            } else if (name) {
                name = false;
            } else if (date) {
                date = false;
            } else if (duration) {
                duration = false;
            } else if (dmg_perc) {
                dmg_perc = false;
            } else if (heal_perc) {
                heal_perc = false;
            } else if (dps) {
                dps = false;
            } else if (hps) {
                hps = false;
            } else if (deaths) {
                deaths = false;
            } else if (crit_heal) {
                crit_heal = false;
            } else if (crit_hit) {
                crit_hit = false;
            } else if (job) {
                job = false;
            } else if (overheal){
                overheal = false;
            } else if (dh) {
                dh = false;
            } else if (crit_direct) {
                crit_direct = false;
            }
        }

        if (qName.equalsIgnoreCase("Row") && isPlayer) {
            isPlayer = false;
            list.add(player);
        }
    }

    public void characters(char ch[], int start, int length)
            throws SAXException {
        if (ally) {
            String allyT = new String(ch, start, length);
            if (allyT.equalsIgnoreCase("T")) {
                player = new Player();
                isPlayer = true;
            }
        }
        data.append(new String(ch, start, length));
    }

    public String getBoss() {
        return boss;
    }

    public LinkedList<Player> getList() {
        return list;
    }
}
