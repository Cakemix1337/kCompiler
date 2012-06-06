package kCompiler.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import kCompiler.functions.Compile;
import kCompiler.functions.Constants;
import kCompiler.functions.Methods;
import net.miginfocom.swing.MigLayout;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.common.io.Resources;

public class MigGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	JTextField rsbotjar = new JTextField(25);
	JTextField source = new JTextField(25);
	JTextField output = new JTextField(25);
	JTextField javac = new JTextField(25);

	private JTextArea log;

	public MigGUI(String String_rsbotJar, String String_source,
			String String_output, String String_javac) {
		super("kCompiler");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		rsbotjar.setText(String_rsbotJar);
		source.setText(String_source);
		output.setText(String_output);
		javac.setText(String_javac);

		JPanel panel = new JPanel();
		panel.setLayout(new MigLayout());
		JMenuBar menuBar = new JMenuBar();
		/** Menu **/
		JMenu menu = new JMenu("Menu");
		menuBar.add(menu);

		JMenuItem menuItem = new JMenuItem("Save");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Constants.PATH_LIB = rsbotjar.getText();

				Constants.PATH_SOURCE = source.getText();

				Constants.PATH_BIN = output.getText();

				Constants.PATH_JAVAC = javac.getText();

				Constants.PREFS.put("rsbotjar", Constants.PATH_LIB);
				Constants.PREFS.put("source", Constants.PATH_SOURCE);
				Constants.PREFS.put("output", Constants.PATH_BIN);
				System.out.println("Saved settings.");
			}
		});
		menu.add(menuItem);
		menuItem = new JMenuItem("About");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		menu.add(menuItem);

		menuItem = new JMenuItem("Exit");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		menu.add(menuItem);
		/** Tools **/
		menu = new JMenu("Tools");
		menuItem = new JMenuItem("Add script from Pastebin & more.");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String input = JOptionPane.showInputDialog("Script:");
				if (input == null || input.trim().isEmpty())
					return;

				if (Constants.PATH_SOURCE.trim().isEmpty()) {
					System.err
							.println("Please apply a source folder to be able to save scripts.");
					return;
				}

				if (!new File(Constants.PATH_SOURCE).canWrite()) {
					System.err.println("Could not write to the source folder.");
					return;
				}
				URL url = null;
				try {
					url = new URL(Methods
							.makeRaw(input));
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				String content = null;
				try {
					content = Resources.toString(url, Charsets.UTF_8);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				if (content == null) {
					System.err.println("Could not get the url.");
					return;
				}

				if (content.contains("<head>")) {
					System.err.println("Invalid raw file.");
					return;
				}

				final Matcher m = Pattern.compile("class\\s*(\\w*)\\s*")
						.matcher(content);

				if (!m.find()) {
					System.out.println("Specified URL is not a script");
					return;
				}

				String class_Name = m.group(1);

				try {
					Files.write(content, new File(Constants.PATH_SOURCE
							+ File.separator + class_Name + ".java"),
							Charsets.UTF_8);
				} catch (IOException e) {
					System.out.println("Could not write file.");
					e.printStackTrace();
				}
			}
		});
		menu.add(menuItem);
		menuBar.add(menu);
		menuBar.add(Box.createHorizontalGlue());
		/** Options **/
		final JMenu commenu = new JMenu("Compile");
		commenu.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				commenu.setEnabled(false);
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						if (rsbotjar.getText().trim().isEmpty()) {
							rsbotjar.setBackground(Color.RED);
							return;
						} else
							rsbotjar.setBackground(Color.WHITE);

						if (source.getText().trim().isEmpty()) {
							source.setBackground(Color.RED);
							return;
						} else
							source.setBackground(Color.WHITE);

						if (output.getText().trim().isEmpty()) {
							output.setBackground(Color.RED);
							return;
						} else
							output.setBackground(Color.WHITE);

						if (javac.getText().trim().isEmpty()) {
							javac.setBackground(Color.RED);
							return;
						} else
							javac.setBackground(Color.WHITE);

						Constants.LOG = "";

						Constants.PATH_LIB = rsbotjar.getText();

						Constants.PATH_SOURCE = source.getText();

						Constants.PATH_BIN = output.getText();

						Constants.PATH_JAVAC = javac.getText();

						Constants.PREFS.put("rsbotjar", Constants.PATH_LIB);
						Constants.PREFS.put("source", Constants.PATH_SOURCE);
						Constants.PREFS.put("output", Constants.PATH_BIN);

						try {
							new Compile();
						} catch (IOException es) {
							es.printStackTrace();
						}

						log.setText(Constants.LOG);
						commenu.setEnabled(true);
					}
				});
			}
		});
		menuBar.add(commenu);

		{
			JButton button = new JButton("Browse");
			panel.add(new JLabel("RSBot.jar:", JLabel.RIGHT), "right, skip");
			panel.add(rsbotjar, "growx");
			panel.add(button, "span");
		}

		{
			JButton button = new JButton("Browse");
			panel.add(new JLabel("Source folder:", JLabel.RIGHT), "right, skip");
			panel.add(source, "growx");
			panel.add(button, "span");
		}

		{
			JButton button = new JButton("Browse");
			panel.add(new JLabel("Output folder:", JLabel.RIGHT), "right, skip");
			panel.add(output, "growx");
			panel.add(button, "span");
		}

		{
			JButton button = new JButton("Browse");
			panel.add(new JLabel("Javac folder:", JLabel.RIGHT), "right, skip");
			panel.add(javac, "growx");
			panel.add(button, "span");
		}
/* TODO:
 * if(advanced)
 * show add ARGS
 * +- in args?
 */
		panel.add(new JLabel("Log:", JLabel.RIGHT), "tag k, span,span");
		log = new JTextArea(5, 22);
		JScrollPane logscroll = new JScrollPane(log);

		panel.add(logscroll, "growx, span");
		add(menuBar, BorderLayout.NORTH);
		add(panel, BorderLayout.SOUTH);
		pack();

		setVisible(true);
	}

}
