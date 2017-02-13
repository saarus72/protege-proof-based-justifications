package not.org.saa.protege.justifications;

import java.util.HashSet;
import java.util.Set;

import org.liveontologies.puli.GenericInferenceSet;
import org.liveontologies.puli.JustifiedInference;
import org.protege.editor.owl.OWLEditorKit;
import org.semanticweb.elk.justifications.BottomUpJustificationComputation;
import org.semanticweb.elk.justifications.DummyMonitor;
import org.semanticweb.elk.justifications.JustificationComputation;
import org.semanticweb.elk.owlapi.ElkProver;
import org.semanticweb.elk.owlapi.ElkProverFactory;
import org.semanticweb.elk.proofs.adapters.InferenceSets;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.semanticweb.owlapi.reasoner.InferenceType;

public class JustificationLogic {

	private OWLEditorKit editorKit;

	public JustificationLogic(OWLEditorKit ek) {
		this.editorKit = ek;
	}

	public Set<? extends Set<OWLAxiom>> getProofBasedJustifications(OWLAxiom entailment) {

		OWLOntology owlOntology = editorKit.getOWLModelManager().getActiveOntology();
		ElkProverFactory proverFactory = new ElkProverFactory();
		ElkProver reasoner = proverFactory.createReasoner(owlOntology);
		reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY);

		OWLAxiom conclusion = entailment;

		final GenericInferenceSet<OWLAxiom, ? extends JustifiedInference<OWLAxiom, OWLAxiom>> inferenceSet = InferenceSets
				.justifyAsserted(reasoner.getProof(conclusion), reasoner.getRootOntology().getAxioms(Imports.EXCLUDED));

		JustificationComputation.Factory<OWLAxiom, OWLAxiom> computationFactory = BottomUpJustificationComputation
				.<OWLAxiom, OWLAxiom>getFactory();

		final JustificationComputation<OWLAxiom, OWLAxiom> computation = computationFactory.create(inferenceSet,
				DummyMonitor.INSTANCE);

		final Set<Set<OWLAxiom>> justifications = new HashSet<Set<OWLAxiom>>(
				computation.computeJustifications(conclusion));

		return justifications;
	}
}
