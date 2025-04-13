package gameFrame;

import java.io.*;

public class ConfigManager {
    private int ret[] = {1024,2,10,10};
    public int[] read(){
        File config = new File("engine.config");
        if (config.exists()){
            try {
                BufferedReader br = new BufferedReader(new FileReader(config));
                String tmp = br.readLine();
                while(tmp != null && tmp.length()!=0){
                    String[] split = tmp.split(":");
                    split[0] = split[0].replaceAll("\\s+", " ").toLowerCase();
                    switch (split[0]){
                        case "hash":
                            ret[0] = Integer.parseInt(split[1].replaceAll("\\s+", " "));
                            break;
                        case "threads":
                            ret[1] = Integer.parseInt(split[1].replaceAll("\\s+", " "));
                            break;
                        case "skill":
                            int tmpskill = Integer.parseInt(split[1].replaceAll("\\s+", " "));
                            if (tmpskill<21&&tmpskill>0) {
                                ret[2] = tmpskill;
                            }
                            break;
                        case "depth":
                            ret[3] = Integer.parseInt(split[1].replaceAll("\\s+", " "));
                            break;
                    }
                    tmp = br.readLine();
                }
                br.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return ret;
    }
    public void write(int[] values){
        FileOutputStream fos;
        for (int i = 0; i < 4; i++) {
            if (values[i]==-1){
                values[i]=ret[i];
            }
        }
        try {
            fos = new FileOutputStream("engine.config");
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write("Hash:"+values[0]+"\n");
            bw.write("Threads:"+values[1]+"\n");
            bw.write("Skill:"+values[2]+"\n");
            bw.write("Depth:"+values[3]+"\n");
            bw.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
