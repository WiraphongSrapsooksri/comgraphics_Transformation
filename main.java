import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;

public class main extends JFrame {
    public main() {
        drawImage Panel = new drawImage();
        Panel.addMouseListener(new MouseAdapter() {
            int x1, y1, x2, y2;
            int i=0;
            @Override
            public void mouseClicked(MouseEvent e) {
                i=i+1;
                if(i%2==1) {
                    x1 = e.getX();
                    y1 = e.getY();
                }else{
                    x2 = e.getX();
                    y2 = e.getY();
                    int x=x2-x1;
                    int y=y2-y1;
                    Panel.translationTri(getGraphics(),x, y);
                }


            }
        });
        this.add(Panel);
        setLayout(null);
    }

    public static void main(String[] args) {
        main f = new main();
        f.setSize(800, 600);
        f.setVisible(true);

        Color c = new Color(255, 255, 255);
        f.getContentPane().setBackground(c);
    }
}

class drawImage extends JPanel implements ActionListener {
    int x1 = 100;
    int y1 = 250;
    int x2 = 200;
    int y2 = 250;
    int x3 = 200;
    int y3 = 100;
    int x1new, y1new, x2new, y2new, x3new, y3new;
    int ev = 0;
    JButton btscale, btreflec, btshear, btclear;

    public drawImage() {
        setSize(800, 600);
        setLayout(null);
        JToolBar toolbar = new JToolBar();
        btscale = new JButton("scale");
        btreflec = new JButton("reflec");
        btshear = new JButton("shear");
        btclear = new JButton("clear");

        toolbar.add(btscale);
        toolbar.add(btreflec);
        toolbar.add(btshear);
        toolbar.add(btclear);

        toolbar.setBounds(0, 0, 600, 50);
        add(toolbar);
        btscale.addActionListener(this);
        btreflec.addActionListener(this);
        btshear.addActionListener(this);
        btclear.addActionListener(this);

    }


    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.black);
//		g.translate(350, 350);
//		rotate(g,180);
        if (ev == 1) {
            g.setColor(Color.black);
            drawtri(g);
            g.setColor(Color.BLACK);
            scalTri(g);
            repaint();
        }
        else if (ev == 0) {
            g.setColor(Color.black);
            drawtri(g);
            repaint();
        }else if (ev == 2) {
            g.setColor(Color.black);
            drawtri(g);
            g.setColor(Color.PINK);
            g.translate(500, 0);
            reflectTri(g);
            repaint();
            g.translate(-500, 0);
        }
        else if (ev == 3) {
            g.setColor(Color.black);
            drawtri(g);
            g.setColor(Color.GREEN);
            shearTri(g);
            repaint();
        }
    }

    void drawtri(Graphics g) {

        ddaLine(g, x1, y1, x2, y2);// 1
        ddaLine(g, x2, y2, x3, y3);// 2
        ddaLine(g, x3, y3, x1, y1);// 3
    }

    void scalTri(Graphics g) {
        int sx = 3;
        int sy = 2;

        x1new = scalX(x1, sx);
        y1new = scalY(y1, sy);
        x2new = scalX(x2, sx);
        y2new = scalY(y2, sy);
        x3new = scalX(x3, sx);
        y3new = scalY(y3, sy);

        ddaLine(g, x1new, y1new, x2new, y2new);// 1
        ddaLine(g, x2new, y2new, x3new, y3new);// 2
        ddaLine(g, x3new, y3new, x1new, y1new);// 3
    }

    int scalX(int x, int sx) {
        return x * sx;
    }
    int scalY(int y, int sy) {
        return y * sy;
    }
    void translationTri(Graphics g,int x,int y) {
        int cx=x;
        int cy=y;
        x1 = translationx(x1, cx);
        y1 = translationy(y1, cy);
        x2= translationx(x2, cx);
        y2= translationy(y2, cy);
        x3= translationx(x3, cx);
        y3= translationy(y3, cy);
        ddaLine(g, x1, y1, x2, y2);// 1
        ddaLine(g, x2, y2, x3, y3);// 2
        ddaLine(g, x3, y3, x1, y1);// 3

    }
    int translationx(int x, int cx) {
        return x + cx;
    }
    int translationy(int y, int cy) {
        return y + cy;
    }

    void reflectTri(Graphics g) {

        x1new = reflectY(x1);
        y1new = y1;
        x2new = reflectY(x2);
        y2new = y2;
        x3new = reflectY(x3);
        y3new = y3;

        ddaLine(g, x1new, y1new, x2new, y2new);// 1
        ddaLine(g, x2new, y2new, x3new, y3new);// 2
        ddaLine(g, x3new, y3new, x1new, y1new);// 3
    }
    int reflectY(int y) {
        return y * -1;
    }
    void shearTri(Graphics g) {
        int shx=2;
        x1new = shearX(x1,shx,y1);
        y1new = y1;
        x2new = shearX(x2,shx,y2);
        y2new = y2;
        x3new = shearX(x3,shx,y3);
        y3new = y3;

        ddaLine(g, x1new, y1new, x2new, y2new);// 1
        ddaLine(g, x2new, y2new, x3new, y3new);// 2
        ddaLine(g, x3new, y3new, x1new, y1new);// 3
    }
    int shearX(int x,int shx,int y){
        return x+shx*y;
    }

    public void ddaLine(Graphics g, int x1, int y1, int x2, int y2) {
        double dx, dy;
        double x, y;
        dx = x2 - x1;
        dy = y2 - y1;
        double m = dx / dy;
        double step;
        double xinc, yinc;

        if (Math.abs(dx) > Math.abs(dy))
            step = Math.abs(dx);
        else
            step = Math.abs(dy);

        xinc = (dx / step);
        yinc = (dy / step);
        x = x1;
        y = y1;

        g.fillOval((int) x, (int) y, 2, 2);

        for (float i = 0; i < step; i++) {
            x = x + xinc;
            y = y + yinc;
            g.fillOval((int) x, (int) y, 2, 2);
        }

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btscale) {
            ev = 1;
            repaint();
        }else if(e.getSource() == btreflec) {
            ev = 2;
            repaint();
        }else if(e.getSource() == btshear) {
            ev = 3;
            repaint();
        }
        else if(e.getSource() == btclear) {
            ev = 0;
            repaint();
        }

    }

}