package not.org.saa.protege.justifications;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.liveontologies.owlapi.proof.OWLProver;
import org.liveontologies.puli.InferenceJustifier;
import org.liveontologies.puli.InferenceSet;
import org.protege.editor.owl.OWLEditorKit;
import org.liveontologies.puli.justifications.InterruptMonitor;
import org.liveontologies.puli.justifications.JustificationComputation;
import org.liveontologies.puli.justifications.ResolutionJustificationComputation;
import org.liveontologies.puli.InferenceSets;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.semanticweb.owlapi.reasoner.InferenceType;

import not.org.saa.protege.explanation.joint.service.JustificationComputationListener;

/**
 * 
 * @author Alexander
 * Date: 13/02/2017
 */

public class JustificationLogic {

	private OWLEditorKit editorKit;
	private List<JustificationComputationListener> listeners;
	private boolean isInterrupted = false;
	
	public JustificationLogic(OWLEditorKit ek) {
		this.editorKit = ek;
		listeners = new ArrayList<JustificationComputationListener>();
	}

	public void computeProofBasedJustifications(OWLAxiom entailment, OWLProver prover) {
		if (prover == null)
			return;

		prover.precomputeInferences(InferenceType.CLASS_HIERARCHY);
		InferenceSet<OWLAxiom> proof = prover.getProof(entailment);
		Set<OWLAxiom> axioms = prover.getRootOntology().getAxioms(Imports.EXCLUDED);
		InferenceSet<OWLAxiom> inferenceSet = InferenceSets.addAssertedInferences(proof, axioms);

		InferenceJustifier<OWLAxiom, ? extends Set<? extends OWLAxiom>> justifier = InferenceSets
				.justifyAssertedInferences();

		ResolutionJustificationComputation.Factory<OWLAxiom, OWLAxiom> computationFactory = ResolutionJustificationComputation
				.getFactory();
		SimpleMonitor monitor = new SimpleMonitor();
		final JustificationComputation<OWLAxiom, OWLAxiom> computation = computationFactory.create(inferenceSet,
				justifier, monitor);

		SimpleListener listener = new SimpleListener();
		computation.enumerateJustifications(entailment, listener);
	}
	
	public void addComputationListener(JustificationComputationListener listener) {
		listeners.add(listener);
	}

	public void removeComputationListener(JustificationComputationListener listener) {
		listeners.remove(listener);
	}
	
	public void interruptComputation() {
		isInterrupted = true;
	}

	public boolean isComputationInterrupted() {
		return isInterrupted;
	}

	private class SimpleListener implements JustificationComputation.Listener<OWLAxiom> {

		@Override
		public void newJustification(Set<OWLAxiom> justification) {
			for (JustificationComputationListener listener : listeners)
				listener.foundJustification(justification);
		}
	}
	
	private class SimpleMonitor implements InterruptMonitor {
		
		@Override
		public boolean isInterrupted() {
			return isComputationInterrupted();
		}
	}
}
