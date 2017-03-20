package not.org.saa.protege.justifications;

import java.util.HashSet;
import java.util.Set;

import org.liveontologies.owlapi.proof.OWLProver;
import org.liveontologies.puli.DynamicInferenceSet;
import org.liveontologies.puli.GenericInferenceSet;
import org.liveontologies.puli.InferenceSet;
import org.liveontologies.puli.JustifiedInference;
import org.protege.editor.owl.OWLEditorKit;
import org.semanticweb.elk.justifications.BottomUpJustificationComputation;
import org.semanticweb.elk.justifications.DummyMonitor;
import org.semanticweb.elk.justifications.JustificationComputation;
import org.semanticweb.elk.proofs.adapters.InferenceSets;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

/**
 * 
 * @author Alexander
 * Date: 13/02/2017
 */

public class JustificationLogic {

	private OWLEditorKit editorKit;

	public JustificationLogic(OWLEditorKit ek) {
		this.editorKit = ek;
	}

	public Set<? extends Set<OWLAxiom>> getProofBasedJustifications(OWLAxiom entailment, OWLProver prover) {
		if (prover == null)
			return null;
		prover.precomputeInferences(InferenceType.CLASS_HIERARCHY);

		JustificationComputation.Factory<OWLAxiom, OWLAxiom> computationFactory = BottomUpJustificationComputation
				.<OWLAxiom, OWLAxiom>getFactory();

		InferenceSet<OWLAxiom> proof = prover.getProof(entailment);
		Set<OWLAxiom> axioms = prover.getRootOntology().getAxioms(Imports.EXCLUDED);
		final GenericInferenceSet<OWLAxiom, ? extends JustifiedInference<OWLAxiom, OWLAxiom>> inferenceSet = InferenceSets
				.justifyAsserted(proof, axioms);

		final JustificationComputation<OWLAxiom, OWLAxiom> computation = computationFactory.create(inferenceSet,
				DummyMonitor.INSTANCE);

		final Set<Set<OWLAxiom>> justifications = new HashSet<Set<OWLAxiom>>(
				computation.computeJustifications(entailment));

		return justifications;
	}
}
