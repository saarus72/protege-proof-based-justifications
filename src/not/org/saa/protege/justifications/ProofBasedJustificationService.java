package not.org.saa.protege.justifications;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.semanticweb.owlapi.model.OWLAxiom;

import not.org.saa.protege.explanation.joint.service.ComputationService;
import not.org.saa.protege.explanation.joint.service.JustificationComputation;
import not.org.saa.protege.justifications.service.ProverService;

/**
 * 
 * @author Alexander
 * Date: 10/02/2017
 */

public class ProofBasedJustificationService extends ComputationService {

	private ProverServiceManager manager;
	private ProverSwitchPanel panel;
	
	@Override
	public JustificationComputation createJustificationComputation(OWLAxiom entailment) {
		return new JustificationComputator(entailment, getOWLEditorKit(), manager);
	}
	
	public boolean canComputeJustification(OWLAxiom entailment) {
		return manager.getServices().size() != 0;
	}

	@Override
	public void initialise() throws Exception {
		manager = new ProverServiceManager("not.org.saa.protege.justifications", "ProverService");
		panel = manager.getServices().size() == 0 ? null : new ProverSwitchPanel();
	}

	@Override
	public void dispose() {
	}

	@Override
	public String getName() {
		return "Proof Based Justification Service";
	}
	
	@Override
	public JPanel getSettingsPanel() {
		return panel;
	}
	
	private class ProverSwitchPanel extends JPanel {
		
		public ProverSwitchPanel() {
			Collection<ProverService> services = manager.getServices();
			switch (services.size()) {
			case 0:
				break;
			case 1:
				manager.selectService(services.iterator().next());
				setLayout(new BorderLayout());
				JLabel label = new JLabel("Using " + manager.getSelectedService() + " as a prover service");
				add(label, BorderLayout.NORTH);
				break;
			default:
				setLayout(new BorderLayout());
				JComboBox<ProverService> selector = new JComboBox<ProverService>();
				for (ProverService service : services)
					selector.addItem(service);
				selector.setSelectedItem(services.iterator().next());
				manager.selectService(services.iterator().next());
				selector.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						manager.selectService((ProverService) selector.getSelectedItem());
						settingsChanged();
					}
				});
				add(selector, BorderLayout.NORTH);
			}
		}
	}
}