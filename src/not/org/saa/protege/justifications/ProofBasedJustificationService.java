package not.org.saa.protege.justifications;

import org.semanticweb.owlapi.model.OWLAxiom;

import not.org.saa.protege.explanation.joint.service.ComputationService;
import not.org.saa.protege.explanation.joint.service.JustificationComputation;

public class ProofBasedJustificationService extends ComputationService {

	@Override
	public JustificationComputation createJustificationComputation(OWLAxiom entailment) {
		return new JustificationComputator(entailment, getOWLEditorKit());
	}
	
	@Override
	public void initialise() throws Exception {
	}


	@Override
	public void dispose() {
	}

	@Override
	public String getName() {
		return "Proof Based Justification Service";
	}
}