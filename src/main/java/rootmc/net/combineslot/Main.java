package rootmc.net.combineslot;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.server.QueryRegenerateEvent;
import cn.nukkit.plugin.PluginBase;
import query.MCQuery;
import query.QueryResponse;

import java.util.List;

public class Main extends PluginBase implements Listener {
    private List<String> listSv;
    @Override
    public void onEnable() {
        saveDefaultConfig();
        this.getServer().getPluginManager().registerEvents(this,this);
        listSv = (List<String>) getConfig().get("servers");
    }

    @EventHandler
    public void onquery(QueryRegenerateEvent event){
        int players = getServer().getOnlinePlayers().size();
        for (String sv: listSv) {
            String[] data = sv.split(":");
            try{
                query.MCQuery mcQuery = new MCQuery(data[0],Integer.parseInt(data[1]));
                QueryResponse response = mcQuery.basicStat();
                players += response.getOnlinePlayers();
            }finally {
                players += 0;
            }
        }
        event.setPlayerCount(players);
    }
}
