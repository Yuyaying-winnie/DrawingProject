package GUI.controller.historic;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

/**
 * 历史记录容器
 */
public class HistoricController implements Serializable {


    public ArrayList<ActionPanel> actions;


    private int size = 0;
    public int id;
    public int modify = 0;

    public HistoricController(int id, BufferedImage img) {
        this.id = id;
        this.actions = new ArrayList<ActionPanel>();
        this.actions.add(new ActionPanel("Open Image", img));
    }

    public HistoricController(int id, BufferedImage img, ArrayList<ActionPanel> actions) {
        this.id = id;
        this.actions = actions;
        this.actions.add(new ActionPanel("Open Image", img));
    }


    public void print() {
        for (ActionPanel action : this.actions) {
            System.out.println(action.getName());
        }
    }

    public void add(ActionPanel a) {

        if (modify == 0) {
            if (size != 0 && actions.size() >= size)
                this.actions = new ArrayList<ActionPanel>(this.actions.subList(1, size));
            actions.add(a);
            return;
        }
        //This line is here to fix a reference bug
        actions.add(0,new ActionPanel(getLastHistoricName(),getHistoricImage()));
        this.actions = new ArrayList<ActionPanel>(this.actions.subList(1, this.actions.size() - modify));
        modify = 0;
        this.actions.add(a);
        buildImages();
        return;

    }

    public void undo() {
        if (modify < this.actions.size() - 1)
            modify++;

    }

    public void redo() {
        if (modify != 0)
            modify--;
    }

    public void buildImages() {
        for (ActionPanel action : this.actions) {
            action.rebuildImages();
        }
    }

    public BufferedImage getHistoricImage() {
        int i = this.actions.size() - modify - 1;
        return this.actions.get(i).getImg();
    }

    public String getLastHistoricName() {
        int i = this.actions.size() - modify - 1;
        return this.actions.get(i).getName();
    }

    public BufferedImage getLastHistoricImage() {
        int i = this.actions.size() - modify - 1;
        return this.actions.get(i).getImg();
    }

    public Vector<String> getActionsNames() {
        Vector<String> actionsNames = new Vector<String>();
        for (ActionPanel action : this.actions) {
            actionsNames.add(action.getName());
        }
        return actionsNames;
    }


    public BufferedImage getLastimg() {
        return this.actions.get(this.actions.size() - 1).getImg();
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isLast() {
        if (modify == 0 || modify == this.actions.size())
            return true;
        return false;
    }

    public boolean isEmpty() {
        return this.actions.isEmpty();
    }

    public int getCurrentId() {
        return this.actions.size() - modify - 1;
    }

    public ActionPanel get(int i) {
        return actions.get(i);
    }

    public void setSize(int i) {
        this.size = i;
        if (this.actions.size() < i) this.actions = new ArrayList<>(this.actions.subList(0, this.actions.size()));
        else this.actions = new ArrayList<>(this.actions.subList(0, i));
        modify = 0;
    }

}
