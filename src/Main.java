import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Main extends JFrame {
    private BST tree;
    private JTextField insertField, deleteField;
    private DrawPanel drawPanel; // Declaring drawPanel as a field

    public Main() {
        tree = new BST();
        setTitle("Binary Tree Traversals");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(23, 63, 99));
        JLabel titleLabel = new JLabel("Binary Tree Traversals");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Drawing Panel
        drawPanel = new DrawPanel(); // Initializing drawPanel
        drawPanel.setBackground(new Color(240, 240, 240));
        add(drawPanel, BorderLayout.CENTER);

        // Operations Panel
        JPanel operationsPanel = new JPanel();
        operationsPanel.setLayout(new GridLayout(10, 1));
        operationsPanel.setBackground(new Color(240, 240, 240));
        operationsPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel operationsLabel = new JLabel("Operations");
        operationsLabel.setFont(new Font("Arial", Font.BOLD, 24));
        operationsLabel.setForeground(new Color(23, 63, 99));
        operationsPanel.add(operationsLabel);

        insertField = new JTextField();
        JButton insertButton = new JButton("INSERT");
        insertButton.addActionListener(new InsertListener());
        operationsPanel.add(new JLabel("Insert Elements:"));
        operationsPanel.add(insertField);
        operationsPanel.add(insertButton);

        deleteField = new JTextField();
        JButton deleteButton = new JButton("DELETE");
        deleteButton.addActionListener(new DeleteListener());
        operationsPanel.add(new JLabel("Delete Elements:"));
        operationsPanel.add(deleteField);
        operationsPanel.add(deleteButton);

        JButton resetButton = new JButton("RESET TREE");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tree = new BST();
                drawPanel.repaint();
            }
        });
        operationsPanel.add(resetButton);

        JLabel traversalsLabel = new JLabel("Traversals");
        traversalsLabel.setFont(new Font("Arial", Font.BOLD, 24));
        traversalsLabel.setForeground(new Color(23, 63, 99));
        operationsPanel.add(traversalsLabel);

        JButton preorderButton = new JButton("PREORDER");
        preorderButton.addActionListener(new TraversalListener("preorder"));
        operationsPanel.add(preorderButton);

        JButton inorderButton = new JButton("INORDER");
        inorderButton.addActionListener(new TraversalListener("inorder"));
        operationsPanel.add(inorderButton);

        JButton postorderButton = new JButton("POSTORDER");
        postorderButton.addActionListener(new TraversalListener("postorder"));
        operationsPanel.add(postorderButton);

        add(operationsPanel, BorderLayout.WEST);

        setVisible(true);
    }

    private class InsertListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String[] items = insertField.getText().split(",");
            for (String item : items) {
                try {
                    int value = Integer.parseInt(item.trim());
                    tree.insert(value);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid input: " + item);
                }
            }
            insertField.setText("");
            drawPanel.repaint();
        }
    }

    private class DeleteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String[] items = deleteField.getText().split(",");
            for (String item : items) {
                try {
                    int value = Integer.parseInt(item.trim());
                    tree.delete(value);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid input: " + item);
                }
            }
            deleteField.setText("");
            drawPanel.repaint();
        }
    }

    private class TraversalListener implements ActionListener {
        private String type;

        public TraversalListener(String type) {
            this.type = type;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String traversalResult = "";
            switch (type) {
                case "preorder":
                    List<Integer> preorderResult = tree.preorder();
                    traversalResult = "Preorder Traversal: " + formatTraversalResult(preorderResult);
                    break;
                case "inorder":
                    List<Integer> inorderResult = tree.inorder();
                    traversalResult = "Inorder Traversal: " + formatTraversalResult(inorderResult);
                    break;
                case "postorder":
                    List<Integer> postorderResult = tree.postorder();
                    traversalResult = "Postorder Traversal: " + formatTraversalResult(postorderResult);
                    break;
            }
            JOptionPane.showMessageDialog(null, traversalResult);
        }

        // Format the traversal result
        private String formatTraversalResult(List<Integer> result) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < result.size(); i++) {
                builder.append(result.get(i));
                if (i < result.size() - 1) {
                    builder.append(", ");
                }
            }
            return builder.toString();
        }
    }


    private class DrawPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawTree(g, tree.root, getWidth() / 2, 30, getWidth() / 4, 60);
        }

        private void drawTree(Graphics g, BST.Node node, int x, int y, int xOffset, int yOffset) {
            if (node != null) {
                g.setColor(new Color(23, 63, 99));
                g.fillOval(x - 25, y - 25, 50, 50);
                g.setColor(Color.WHITE);
                g.drawOval(x - 25, y - 25, 50, 50);
                g.drawString(String.valueOf(node.data), x - 10, y + 5);

                if (node.left != null) {
                    g.drawLine(x, y, x - xOffset, y + yOffset);
                    drawTree(g, node.left, x - xOffset, y + yOffset, xOffset / 2, yOffset);
                }

                if (node.right != null) {
                    g.drawLine(x, y, x + xOffset, y + yOffset);
                    drawTree(g, node.right, x + xOffset, y + yOffset, xOffset / 2, yOffset);
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}
